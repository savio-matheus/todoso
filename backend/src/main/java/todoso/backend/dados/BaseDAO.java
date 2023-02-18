package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public interface BaseDAO {
	public long inserir(BaseDTO dto) throws SQLException;
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException;
	public long atualizar(BaseDTO dto) throws SQLException;
	public long excluir(BaseDTO filtros) throws SQLException;
}
