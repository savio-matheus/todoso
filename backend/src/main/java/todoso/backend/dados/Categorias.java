package todoso.backend.dados;

import java.util.ArrayList;
import java.sql.SQLException;

public class Categorias implements Dados {
	
	public Categorias() {}

	public int inserir(BaseDTO tarefa) throws SQLException {
		return 0;
	}

	public int inserir(ArrayList<? extends BaseDTO> tarefas) throws SQLException {
		return 0;
	}

	public ArrayList<CategoriaDTO> selecionar(BaseDTO filtros) throws SQLException {
		return null;
	}

	public int atualizar(BaseDTO tarefa) throws SQLException {
		return 0;
	}

	public int atualizar(ArrayList<? extends BaseDTO> tarefas) throws SQLException {
		return 0;
	}

	public int excluir(BaseDTO filtros) throws SQLException {
		return 0;
	}

}
