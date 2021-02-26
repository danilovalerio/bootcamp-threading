package projetos.danilo.bootcamp_threading

/**
 * Event utilizado para mapear ações que a view deve executar, exemplo:
 *     exibe tela, recolher a lista, navegar, carrega lista...
 */
sealed class MainEvent {
    object ShowLoading : MainEvent()
    object HideLoading : MainEvent()
}
