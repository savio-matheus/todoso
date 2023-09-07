package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public interface BaseDAO {
	public long inserir(BaseDTO dto) throws SQLException, Exception;
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException, Exception;
	public long atualizar(BaseDTO dto) throws SQLException, Exception;
	public long excluir(BaseDTO filtros) throws SQLException, Exception;
}
