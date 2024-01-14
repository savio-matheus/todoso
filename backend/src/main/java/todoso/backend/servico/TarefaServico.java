package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import todoso.backend.excecoes.NotFoundException;
import todoso.backend.dados.TarefasDAO;
import todoso.backend.dados.BdAcesso;
import todoso.backend.dados.CategoriaDAO;
import todoso.backend.dados.CategoriaDTO;
import todoso.backend.dados.TagDTO;
import todoso.backend.dados.TagsDAO;
import todoso.backend.dados.TarefaDTO;

public class TarefaServico {
	final long ID_CATEGORIA_PADRAO = 1;
	private BdAcesso bancoDeDados;
	private TarefasDAO dados;
	private CategoriaDAO catDAO;
	private TagsDAO tagDAO;

	public TarefaServico() throws SQLException {
		bancoDeDados = BdAcesso.abrirConexao(false);
		dados = new TarefasDAO(bancoDeDados);
		catDAO = new CategoriaDAO(bancoDeDados);
		tagDAO = new TagsDAO(bancoDeDados);
	}

	public TarefaDTO criarTarefa(TarefaDTO tarefa) throws SQLException {
		long id;
		id = dados.inserir(tarefa);

		tarefa.setId(id);

		// Relacionar as categorias
		if (tarefa.getCategorias() == null) {
			ArrayList<CategoriaDTO> l = new ArrayList<>();
			CategoriaDTO padrao = new CategoriaDTO();
			padrao.setId(ID_CATEGORIA_PADRAO);
			l.add(padrao);

			tarefa.setCategorias(l);
		}

		int erros = 0;
		for (CategoriaDTO c : tarefa.getCategorias()) {
			if (!catDAO.relacionarTarefaCategoria(tarefa, c)) {
				erros++;
			}
		}

		// Em caso de erros, existe a possibilidade de a tarefa ter ficado
		// sem categoria.
		if (erros > 0) {
			catDAO.relacionarCategoriaPadrao();
		}

		// Relacionar as tags. Nesse caso não existe o conceito de tag padrão.
		if (tarefa.getTags() != null) {
			for (TagDTO t : tarefa.getTags()) {
				// TODO: e se ok = false?
				boolean ok = tagDAO.relacionarTarefaTag(tarefa, t);
			}
		}

		if (id <= 0) {
			bancoDeDados.reverter();
			throw new SQLException("Houve um erro ao criar tarefa.");
		}
		tarefa.setId(id);

		bancoDeDados.close();
		return tarefa;
	}

	public ArrayList<TarefaDTO> selecionarTarefas(TarefaDTO filtros) throws SQLException {

		ArrayList<TarefaDTO> resultados = dados.selecionar(filtros);
		HashMap<Long, ArrayList<CategoriaDTO>> categorias =
			catDAO.selecionarCategoriasPorTarefa(resultados);
		HashMap<Long, ArrayList<TagDTO>> tags =
			tagDAO.selecionarTagsPorTarefa(resultados);

		for (TarefaDTO t : resultados) {
			t.setCategorias(categorias.get(t.getId()));
		}

		for (TarefaDTO t : resultados) {
			t.setTags(tags.get(t.getId()));
		}

		for (TarefaDTO t : resultados) {
			t.setArquivos(dados.anexosPorTarefa(t));
		}

		return resultados;
	}

	public TarefaDTO editarTarefa(TarefaDTO tarefaNova)
		throws SQLException, IllegalArgumentException, NotFoundException {

		if (tarefaNova.getId() == null) {
			throw new IllegalArgumentException("Id cannot be null.");
		}

		long id = dados.atualizar(tarefaNova);

		if (id <= 0) {
			bancoDeDados.reverter();
			throw new NotFoundException("Try a different id.");
		}

		// Retiramos todas as categorias/tags da tarefa do banco e adicionamos
		// novamente para refletir a tarefa editada.
		// TODO: talvez tenha forma melhor de fazer isso

		if (tarefaNova.getCategorias() == null || tarefaNova.getCategorias().isEmpty()) {
			ArrayList<CategoriaDTO> l = new ArrayList<>();
			CategoriaDTO c = new CategoriaDTO();
			c.setId(ID_CATEGORIA_PADRAO);
			l.add(c);
			tarefaNova.setCategorias(l);
		}

		catDAO.desfazerRelacaoTarefaCategoria(tarefaNova, null);
		for (CategoriaDTO c : tarefaNova.getCategorias()) {
			catDAO.relacionarTarefaCategoria(tarefaNova, c);
		}

		tagDAO.desfazerRelacaoTarefaTag(tarefaNova, null);
		for (TagDTO t : tarefaNova.getTags()) {
			tagDAO.relacionarTarefaTag(tarefaNova, t);
		}

		bancoDeDados.confirmar();
		bancoDeDados.fecharConexao();
		return tarefaNova;
	}

	public TarefaDTO deletarTarefa(TarefaDTO filtros)
		throws SQLException, IllegalArgumentException, NotFoundException {

		ArrayList<TarefaDTO> retorno = this.selecionarTarefas(filtros);

		if (retorno.size() == 0) {
			throw new NotFoundException("Id not found. Try a different one.");
		}

		// Tratamento das categorias
		for (CategoriaDTO c : retorno.get(0).getCategorias()) {
			catDAO.desfazerRelacaoTarefaCategoria(filtros, c);
		}

		// Tratamento das tags
		for (TagDTO t : retorno.get(0).getTags()) {
			tagDAO.desfazerRelacaoTarefaTag(filtros, t);
		}
		tagDAO.excluirTagsSemTarefas();

		long id = dados.excluir(filtros);
		filtros.setId(id);

		bancoDeDados.close();
		return filtros;
	}
}