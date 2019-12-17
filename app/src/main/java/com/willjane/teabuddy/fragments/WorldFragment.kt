package com.willjane.teabuddy.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willjane.teabuddy.R
import com.willjane.teabuddy.adapters.CommunityPostAdapter
import com.willjane.teabuddy.utils.Helpers
import com.willjane.teabuddy.utils.models.CommunityPost
import com.willjane.teabuddy.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_tea_world.*

/**
 * Community of TeaBuddy - view, share and interact with other TeaBuddies
 */
class WorldFragment: Fragment(), CommunityPostAdapter.CommunityPostInterface {

    private lateinit var  vm: MainActivityViewModel
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  adapter: CommunityPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_tea_world, container, false) as ViewGroup

        setUpVM()
        recyclerView = rootView.findViewById(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerView.adapter = CommunityPostAdapter(vm, requireContext(), this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.refreshPostsList()
        Log.d(TAG, "posts list size' ${vm.communityPosts.size}")
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.adapter = CommunityPostAdapter(vm, requireContext(), this)

        if(vm.isSignedIn){
            notSignedIn.visibility = View.GONE
            signedInContainer.visibility = View.VISIBLE
            worldUserName.text = vm.currentUser.value?.name

            newPostFab.setOnClickListener {

                showMakePostPopup(it)

            }
        } else{
            signedInContainer.visibility = View.GONE
            notSignedIn.visibility = View.VISIBLE
        }
    }

    private fun setUpVM() {
        Log.d(TAG, "setting up VM")
        vm = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        vm.currentActionPage.value = MainActivityViewModel.TEA_WORLD_PAGE
    }

    //overrided interface call that shows the popup for the post
    override fun showPostPopup(anchorView: View, post: CommunityPost) {
        Log.d(TAG, "showing popup window ")

        val view = layoutInflater.inflate(R.layout.popup_view_communitypost, null)
        val popupWindow = PopupWindow(view, Helpers.dpToPx(350), Helpers.dpToPx(600))

        val postTitle: TextView = view.findViewById(R.id.postName)
        postTitle.text = post.postTitle

        Log.d(TAG, "post description: ${post.postDesc}")
        val postDesc: TextView = view.findViewById(R.id.postDesc)
        postDesc.text = post.postDesc

        val posterName: TextView = view.findViewById(R.id.brewTemp)
        posterName.text = post.posterName

        val heartCount: TextView = view.findViewById(R.id.heartCount)
        heartCount.text = post.postHearts.toString()

        val userImage: ImageView = view.findViewById(R.id.userImage)
        Glide.with(context?:return).load(post.posterURL).into(userImage)

        popupWindow.isFocusable = true
        val locationArray = IntArray(2)
        view.getLocationOnScreen(locationArray)
        popupWindow.showAtLocation(view, Gravity.CENTER,  locationArray[0], locationArray[1] + view.height)

    }

    private fun showMakePostPopup(anchorView: View){
        val view = layoutInflater.inflate(R.layout.popup_make_communitypost, null)
        val popupWindow = PopupWindow(view, Helpers.dpToPx(350), Helpers.dpToPx(600))
        val postTitle: TextView = view.findViewById(R.id.postName)
        val postDesc: TextView = view.findViewById(R.id.postDesc)

        val endButton: Button = view.findViewById(R.id.makePostButton)
        endButton.setOnClickListener {
                vm.newCommunityPost(postTitle.text.toString(), postDesc.text.toString())
                Log.d(TAG, "new post added")
                recyclerView.adapter?.notifyDataSetChanged()
        }

        popupWindow.isFocusable = true
        val locationArray = IntArray(2)
        view.getLocationOnScreen(locationArray)
        popupWindow.showAtLocation(view, Gravity.CENTER,  locationArray[0], locationArray[1] + view.height)

    }
    override fun onResume() {
        super.onResume()
        vm.refreshPostsList()

    }
    companion object{
        private const val TAG = "TeaWorldFragment"
    }
}