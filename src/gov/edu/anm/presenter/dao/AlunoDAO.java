package gov.edu.anm.presenter.dao;

import gov.edu.anm.presenter.jdbc.ConnectionFactory;
import gov.edu.anm.presenter.model.entities.Aluno;
import gov.edu.anm.presenter.model.entities.Equipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    private Connection con;

    public AlunoDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public String alunosDaEquipe(Equipe equipe) throws SQLException {
        String alunosDaEquipe = "";
        try {
            String sql = "select * from tb_alunos where id_equipe = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, equipe.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.isLast()) {
                    alunosDaEquipe += rs.getString("nome") + ".";
                } else {
                    alunosDaEquipe += rs.getString("nome") + ", ";
                }
            }

            stmt.execute();
            stmt.close();
            return alunosDaEquipe;
        } catch (SQLException e) {
            throw new SQLException("Erro na busca dos alunos:\n" + e);
        }
    }

    public void salvarAluno(String nomeAluno, Equipe equipe) throws SQLException {
        Aluno aluno = new Aluno(nomeAluno, equipe);
        try {
            String sql = "insert into tb_alunos (nome, id_equipe) values (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getEquipe().getId());

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new SQLException("Erro no cadastro dos alunos:\n" + e);
        }
    }

    public void excluirAlunos(Equipe equipe) throws SQLException{
        try {
            String sql = "delete from tb_alunos where id_equipe = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, equipe.getId());
            
            stmt.execute();
            stmt.close();            
        } 
        catch (SQLException e) {
            throw new SQLException("Erro ao excluir alunos:\n" + e);
        }
    }
    
}
