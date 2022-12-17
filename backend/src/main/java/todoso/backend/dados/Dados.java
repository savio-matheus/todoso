package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public interface Dados {
	public int inserir(BaseDTO tarefa) throws SQLException;
	public int inserir(ArrayList<? extends BaseDTO> tarefas) throws SQLException;
	public ArrayList<? extends BaseDTO> selecionar(BaseDTO filtros) throws SQLException;
	public int atualizar(BaseDTO tarefa) throws SQLException;
	public int atualizar(ArrayList<? extends BaseDTO> tarefas) throws SQLException;
	public int excluir(BaseDTO filtros) throws SQLException;
}
