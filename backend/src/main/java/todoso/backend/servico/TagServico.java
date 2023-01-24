package todoso.backend.servico;

import java.sql.SQLException;
import java.util.ArrayList;

import todoso.backend.dados.TagDTO;
import todoso.backend.dados.Tags;
import todoso.backend.dados.TarefaDTO;
import todoso.backend.excecoes.NotFoundException;

public class TagServico {
	Tags dados = new Tags();

	public ArrayList<TagDTO> selecionarTags(TagDTO filtros) throws SQLException {

		@SuppressWarnings("unchecked")
		ArrayList<TagDTO> tags = (ArrayList<TagDTO>) dados.selecionar(filtros);
		return tags;
	}

	public TagDTO editarTag(TagDTO tagNova) throws SQLException {
		long tagId = dados.atualizar(tagNova);

		if (tagId <= 0) {
			throw new SQLException("Update returned an invalid id.");
		}

		TagDTO tag = new TagDTO();
		tag.setId(tagId);

		return tag;
	}

	public ArrayList<TarefaDTO> selecionarTarefasPorTag(TagDTO filtros) {

		return null;
	}
}