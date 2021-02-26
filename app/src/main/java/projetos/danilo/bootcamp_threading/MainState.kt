package projetos.danilo.bootcamp_threading
/**
 * States utilizamos para mapear o estado do objeto, exemplo:
 *     uma lista pode estar vazia, ter um erro na chamada ou a lista com sucesso.
 */
sealed class MainState {
    //state de teste exibindo msg no log
    data class MensagemBoasVindas(val string: String): MainState()
    data class ListaAstrosPeople(val listaAstrosPeople: List<AstrosPeople>): MainState()
    data class ListaAstrosPeopleVazia(val string: String): MainState()
}
