package io.legado.app.ui.bookinfo

import android.os.Bundle
import androidx.lifecycle.Observer
import io.legado.app.R
import io.legado.app.base.VMBaseActivity
import io.legado.app.data.entities.Book
import io.legado.app.help.ImageLoader
import io.legado.app.utils.getViewModel
import io.legado.app.utils.gone
import io.legado.app.utils.visible
import kotlinx.android.synthetic.main.activity_book_info.*

class BookInfoActivity : VMBaseActivity<BookInfoViewModel>(R.layout.activity_book_info) {
    override val viewModel: BookInfoViewModel
        get() = getViewModel(BookInfoViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel.bookData.observe(this, Observer { showBook(it) })
        viewModel.loadData(intent)
    }

    private fun showBook(book: Book) {
        tv_name.text = book.name
        tv_author.text = getString(R.string.author_show, book.author)
        tv_origin.text = getString(R.string.origin_show, book.originName)
        tv_lasted.text = getString(R.string.lasted_show, book.latestChapterTitle)
        tv_intro.text = getString(R.string.intro_show, book.getDisplayIntro())
        book.getDisplayCover()?.let {
            ImageLoader.load(this, it)
                .placeholder(R.drawable.img_cover_default)
                .error(R.drawable.img_cover_default)
                .centerCrop()
                .setAsDrawable(iv_cover)
        }
        val kinds = book.getKindList()
        if (kinds.isEmpty()) {
            ll_kind.gone()
        } else {
            ll_kind.visible()
            for (index in 0..2) {
                if (kinds.size > index) {
                    when (index) {
                        0 -> {
                            tv_kind.text = kinds[index]
                            tv_kind.visible()
                        }
                        1 -> {
                            tv_kind_1.text = kinds[index]
                            tv_kind_1.visible()
                        }
                        2 -> {
                            tv_kind_2.text = kinds[index]
                            tv_kind_2.visible()
                        }
                    }
                } else {
                    when (index) {
                        0 -> tv_kind.gone()
                        1 -> tv_kind_1.gone()
                        2 -> tv_kind_2.gone()
                    }
                }
            }
        }
    }


}