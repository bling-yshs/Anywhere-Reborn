package com.yshs.anywhere_.ui.settings

import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yshs.anywhere_.R
import com.yshs.anywhere_.adapter.applist.AppListAdapter
import com.yshs.anywhere_.adapter.applist.MODE_ICON_PACK
import com.yshs.anywhere_.constants.Const
import com.yshs.anywhere_.constants.GlobalValues
import com.yshs.anywhere_.model.Settings
import com.yshs.anywhere_.model.viewholder.AppListBean
import com.yshs.anywhere_.utils.AppUtils
import com.yshs.anywhere_.view.app.AnywhereDialogBuilder
import com.yshs.anywhere_.view.app.AnywhereDialogFragment
import com.yshs.anywhere_.viewbuilder.entity.IconPackDialogBuilder

class IconPackDialogFragment : AnywhereDialogFragment() {

    private lateinit var mBuilder: IconPackDialogBuilder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mBuilder = IconPackDialogBuilder(requireContext())
        initView()

        return AnywhereDialogBuilder(requireContext()).setView(mBuilder.root)
                .setTitle(R.string.dialog_title_choose_icon_pack)
                .create()
    }

    private fun initView() {
        val adapter = AppListAdapter(MODE_ICON_PACK)
        val hashMap = Settings.iconPackManager.getAvailableIconPacks(true)
        val listBeans: MutableList<AppListBean> = ArrayList()

        listBeans.add(AppListBean(
                id = Const.DEFAULT_ICON_PACK,
                appName = requireContext().getString(R.string.bsd_default),
                packageName = Const.DEFAULT_ICON_PACK,
                type = -1
        ))
        for ((_, iconPack) in hashMap) {
            listBeans.add(AppListBean(
                    id = iconPack.packageName,
                    appName = iconPack.name,
                    packageName = iconPack.packageName,
                    type = -1
            ))
        }
        adapter.apply {
            setOnItemClickListener { _, _, position ->
                val item = getItem(position)
                GlobalValues.iconPack = item.packageName
                Settings.initIconPackManager()
                AppUtils.restart()
            }
            setList(listBeans)
        }

        mBuilder.rvIconPack.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }
}
