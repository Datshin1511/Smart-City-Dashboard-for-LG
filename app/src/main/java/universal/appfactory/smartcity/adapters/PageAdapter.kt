@file:Suppress("DEPRECATION")

package universal.appfactory.smartcity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import universal.appfactory.aeroindia2023.GridLayoutFragment2
import universal.appfactory.aeroindia2023.GridLayoutFragment3

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
        return when(position){
            0 -> GridLayoutFragment1()
            1 -> GridLayoutFragment2()
            2 -> GridLayoutFragment3()
            else -> GridLayoutFragment1()
        }
    }

}