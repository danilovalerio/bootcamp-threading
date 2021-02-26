package projetos.danilo.bootcamp_threading

/**
 * Interactor usado para mapear as ações do usuário, exemplo:
 * clique no botão carregar, fechar, clique no item da lista entre outros.
 */
sealed class MainInteractor {
    object ClickCarregar : MainInteractor()

}
