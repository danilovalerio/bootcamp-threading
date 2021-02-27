package projetos.danilo.bootcamp_threading

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

