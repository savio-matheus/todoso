package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public interface Dados {
	public int inserir(BaseDTO dto) throws SQLException;
	public int inserir(ArrayList<? extends BaseDTO> dtos) throws SQLException;
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException;
	public int atualizar(BaseDTO dto) throws SQLException;
	public int atualizar(ArrayList<? extends BaseDTO> dtos) throws SQLException;
	public int excluir(BaseDTO filtros) throws SQLException;
}
