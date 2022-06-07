package uz.gita.justcontact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var host : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment // bu global bo`lishi k.k (graph glogab bo`lmasa mayli)

        val graph = host.navController.navInflater.inflate(R.navigation.navigation)
        host.navController.graph = graph

    }
}