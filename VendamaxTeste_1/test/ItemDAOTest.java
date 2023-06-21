import org.junit.Assert;
import org.junit.Test;
import vendamaxteste.ItemDAO;
import vendamaxteste.ItemPrincipal;

import java.util.List;

public class ItemDAOTest {

    @Test
    public void testGetItens() {
        ItemDAO itemDAO = new ItemDAO();

        // Chama o método para obter a lista de itens
        List<ItemPrincipal> itens = itemDAO.getItens();

        // Verifica se a lista de itens não é nula
        Assert.assertNotNull(itens);

        // Verifica se a lista de itens não está vazia
        Assert.assertFalse(itens.isEmpty());
    }

    @Test
    public void testInserirItem() {
        ItemDAO itemDAO = new ItemDAO();

        // Cria um novo item
        ItemPrincipal item = new ItemPrincipal();
        item.setCod("123");
        item.setNome("Item de Teste");
        item.setValor("9.99");

        // Insere o item no banco de dados
        itemDAO.inserirItem(item);

        // Verifica se o item foi inserido corretamente

        // Primeiro, obtemos a lista de itens atualizada
        List<ItemPrincipal> itens = itemDAO.getItens();

        // Verifica se a lista de itens não é nula
        Assert.assertNotNull(itens);

        // Verifica se a lista de itens não está vazia
        Assert.assertFalse(itens.isEmpty());

        // Verifica se o item inserido está presente na lista
        boolean itemEncontrado = false;
        for (ItemPrincipal i : itens) {
            if (i.getCod().equals(item.getCod()) &&
                i.getNome().equals(item.getNome()) &&
                i.getValor().equals(item.getValor())) {
                itemEncontrado = true;
                break;
            }
        }

        // Verifica se o item foi encontrado na lista
        Assert.assertTrue(itemEncontrado);
    }

    // Teste para os demais métodos da classe ItemDAO podem ser implementados da mesma forma.
}
