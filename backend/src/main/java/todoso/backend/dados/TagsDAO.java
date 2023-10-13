package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

public class TagsDAO implements BaseDAO {

	private BdAcesso bd = null;

	public TagsDAO(BdAcesso bd) throws SQLException {
		if (bd != null && bd.conexao != null && !bd.conexao.isClosed()) {
			this.bd = bd;
		}
		else {
			throw new SQLException("Não foi fornecida uma conexão válida " +
				"com o banco de dados.");
		}
	}

	@Override
	public long inserir(BaseDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException {
		// TODO Auto-generated method stub
		return new ArrayList<TagDTO>();
	}

	public ArrayList<? extends BaseDTO> selecionarTarefas(BaseDTO filtros) throws SQLException {
		// TODO
		return null;
	}

	@Override
	public long atualizar(BaseDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long excluir(BaseDTO filtros) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}