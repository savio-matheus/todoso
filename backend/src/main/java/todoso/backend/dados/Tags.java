package todoso.backend.dados;

import java.sql.SQLException;
import java.util.ArrayList;

public class Tags implements Dados {

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