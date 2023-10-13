package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.dados.BdAcesso;
import todoso.backend.dados.TagDTO;
import todoso.backend.dados.TagsDAO;
import todoso.backend.dados.TarefaDTO;

public class TagServico {
	private BdAcesso bancoDeDados;
	private TagsDAO dados;

	public TagServico() throws SQLException {
		bancoDeDados = BdAcesso.abrirConexao(false);
		dados = new TagsDAO(bancoDeDados);
	}

	public ArrayList<TagDTO> selecionarTags(TagDTO filtros) throws SQLException {

		@SuppressWarnings("unchecked")
		ArrayList<TagDTO> tags = (ArrayList<TagDTO>) dados.selecionar(filtros);
		return tags;
	}

	public TagDTO editarTag(TagDTO tagNova) throws SQLException {
		long tagId = dados.atualizar(tagNova);

		if (tagId <= 0) {
			bancoDeDados.reverter();
			throw new SQLException("Update returned an invalid id.");
		}

		TagDTO tag = new TagDTO();
		tag.setId(tagId);

		bancoDeDados.close();
		return tag;
	}

	// TODO
	public ArrayList<TarefaDTO> selecionarTarefasPorTag(TagDTO filtros) {

		return null;
	}
}