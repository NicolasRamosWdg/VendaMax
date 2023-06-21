import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import vendamaxteste.FabricaConexao;

public class FabricaConexaoTest {

    @Test
    public void testGetConexao() {
        try {
            Connection connection = FabricaConexao.getConexao();
            assertNotNull(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
