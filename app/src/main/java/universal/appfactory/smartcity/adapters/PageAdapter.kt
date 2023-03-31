@file:Suppress("DEPRECATION")

package universal.appfactory.smartcity.adapters

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import universal.appfactory.smartcity.home.HomepageActivity
import universal.appfactory.smartcity.home.MapsActivity

class PageAdapter(fm: FragmentManager, pageCount: Int=3): FragmentPagerAdapter(fm) {
    private val pages: Int = pageCount

    override fun getCount(): Int{
        return pages
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Dashboard"
            1 -> "Map"
            2 -> "More Info"
            else -> "N/A"
        }
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        val fragment: Fragment = when(position){
            0 -> {
                bundle.putInt("pageNo", 1)
                GridLayoutFragment1()
            }
            1 -> {
                bundle.putInt("pageNo", 2)
                GridLayoutFragment2()
            }
            2 -> {
                bundle.putInt("pageNo", 3)
                GridLayoutFragment3()
            }
            else -> {
                bundle.putInt("pageNo", 1)
                GridLayoutFragment1()
            }
        }

        fragment.arguments = bundle
        return fragment
    }

}
