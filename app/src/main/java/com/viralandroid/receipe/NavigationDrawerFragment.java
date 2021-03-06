package com.viralandroid.receipe;

/**
 * Created by T on 18-02-2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the  design guidelines for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the
     * user manually expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
  //  private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private MyDrawerAdapter mMyDrawerAdapter;

    private String[] titles;



    private int[] images, selectedposition;
    ArrayList<Categories> categories;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<Category> categoriesfrom_api;

    public NavigationDrawerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated
        // awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState
                    .getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of
        // actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);



        return mDrawerListView;
    }


    public void update_navigation(){

        categories= new ArrayList<>();
        categoriesfrom_api = new ArrayList<>();
//        try {
//            jsonArray = jsonObject.getJSONArray("Category");
//            for (int i =0;i<jsonObject.getJSONArray("Category").length();i++) {
//                categories.add(new Categories(jsonArray.getJSONObject(i)));
//            }
//            JSONObject temp = new JSONObject();
//            temp.put("name","My Favorites");
//            temp.put("icon",R.drawable.favorites);
//            categories.add(new Categories(temp));
//
//            JSONObject temp1 = new JSONObject();
//            temp1.put("name","Shopping List");
//            temp1.put("icon",R.drawable.shopping);
//            categories.add(new Categories(temp1));
//
//            JSONObject temp2 = new JSONObject();
//            temp2.put("name","About Us");
//            temp2.put("icon",R.drawable.about);
//            categories.add(new Categories(temp2));
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading categories..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getContext())
                .load(Session.SERVER_URL+"category.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                            for (int i=0;i<result.size();i++) {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                try {
                                    Log.e("category",result.get(i).getAsJsonObject().toString());
                                    Category category = new Category(result.get(i).getAsJsonObject(), getContext());
                                    categoriesfrom_api.add(category);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                mMyDrawerAdapter.notifyDataSetChanged();
                            }


                        JsonObject jsonObj = new JsonObject();
                        jsonObj.addProperty("id",categoriesfrom_api.size()-2);
                        jsonObj.addProperty("title",Session.GetWord(getContext(),"My Favorites"));
                        jsonObj.addProperty("title_ar",Session.GetWord(getContext(),"My Favorites"));
                        jsonObj.addProperty("image","http://mamacgroup.com/recipies/uploads/category/101489559793.png");
                        jsonObj.addProperty("count","0");
                        categoriesfrom_api.add(new Category(jsonObj,getContext()));

//                        JsonObject temp = new JsonObject();
//                        temp.addProperty("id","8");
                        //8,9
//                        temp.addProperty("title","Shopping List");
//                        temp.addProperty("title_ar","Shopping List");
//                        temp.addProperty("image","http://mamacgroup.com/recipies/uploads/category/81489559514.png");
//                        temp.addProperty("count","0");
//                        categoriesfrom_api.add(new Category(temp,getContext()));

                        JsonObject temp1 = new JsonObject();
                        temp1.addProperty("id",categoriesfrom_api.size()-1);
                        temp1.addProperty("title",Session.GetWord(getContext(),"About Us"));
                        temp1.addProperty("title_ar",Session.GetWord(getContext(),"About Us"));
                        temp1.addProperty("image","http://mamacgroup.com/recipies/uploads/category/91489559668.png");
                        temp1.addProperty("count","0");
                        categoriesfrom_api.add(new Category(temp1,getContext()));

                    }
                });




        images = new int[] { R.drawable.fruit_recipe, R.drawable.beef_recipe,
                R.drawable.chicken_recipe,R.drawable.pasta_recipe,R.drawable.fish_recipe,R.drawable.desserts_recipe,R.drawable.others,R.drawable.favorites,R.drawable.shopping,R.drawable.about};


        selectedposition = new int[] { mCurrentSelectedPosition };

        mMyDrawerAdapter = new MyDrawerAdapter(getActivity(), categoriesfrom_api,images,
                selectedposition);
        mDrawerListView.setAdapter(mMyDrawerAdapter);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedposition[0] = i;
                mMyDrawerAdapter.notifyDataSetChanged();
                if(i==categoriesfrom_api.size()-2) {
                    MyFavoritesFragment myFavoritesFragment = new MyFavoritesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category_obj", categoriesfrom_api.get(i));
                    myFavoritesFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, myFavoritesFragment).addToBackStack("category").commit();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
//                }else if (i==7){
//                    CartFragment cartFragment = new CartFragment();
//                    getFragmentManager().beginTransaction().replace(R.id.container,cartFragment).addToBackStack("cart").commit();
//                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                }
               else if (i==categoriesfrom_api.size()-1) {
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container, aboutUsFragment).addToBackStack("about").commit();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    selectItem(i);
                    ProductsFragment productsFragment = new ProductsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category_obj",categoriesfrom_api.get(i));
                    productsFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container,productsFragment).addToBackStack("category").commit();
                }


            }
        });
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation
     * drawer interactions.
     *
     * @param fragmentId
     *            The android:id of this fragment in its activity's layout.
     * @param drawerLayout
     *            The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.fruit_recipe,
                GravityCompat.START);
        // set up the drawer's list view with items and click listener

     //   ActionBar actionBar = getActionBar();
     //   actionBar.setDisplayHomeAsUpEnabled(true);
      //  actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
      //  mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
        //       mDrawerLayout, /* DrawerLayout object */
        //       R.drawable.fruit_recipe, /* nav drawer image to replace 'Up' caret */
        //     R.string.navigation_drawer_open, /*
        //     * "open drawer" description for
        //   * accessibility
        // */
        //         R.string.navigation_drawer_close /*
        //   * "close drawer" description for
        //    * accessibility
        //    */
        // ) {
        //   @Override
        //    public void onDrawerClosed(View drawerView) {
        //       super.onDrawerClosed(drawerView);
        //       if (!isAdded()) {
        //           return;
        //       }

        //       getActivity().supportInvalidateOptionsMenu(); // calls
        //       // onPrepareOptionsMenu()
        //   }

          //  @Override
         //   public void onDrawerOpened(View drawerView) {
             //   super.onDrawerOpened(drawerView);
              //  if (!isAdded()) {
              //      return;
               // }
//
               // if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to
                    // prevent auto-showing
                    // the navigation drawer automatically in the future.
                //    mUserLearnedDrawer = true;
                 //   SharedPreferences sp = PreferenceManager
                    //        .getDefaultSharedPreferences(getActivity());
                 //   sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
                  //          .commit();
               // }

                getActivity().supportInvalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            //}
        //};

        // If the user hasn't 'learned' about the drawer, open it to introduce
        // them to the drawer,
        // per the navigation drawer design guidelines.
      //  if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
      //      mDrawerLayout.openDrawer(mFragmentContainerView);
      //  }

        // Defer code dependent on restoration of previous instance state.
       // mDrawerLayout.post(new Runnable() {
       //     @Override
       //     public void run() {
        //        mDrawerToggle.syncState();
      ///      }
      //  });

      /////  mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
      //  mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar.
        // See also
        // showGlobalContextActionBar, which controls the top-left area of the
        // action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  if (mDrawerToggle.onOptionsItemSelected(item)) {
      ////      return true;
       // }

      //  if (item.getItemId() == R.id.action_example) {
      //      Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT)
      //              .show();
      ////      return true;
     //   }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to
     * show the global app 'context', rather than just what's in the current
     * screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }


    /**
     * Callbacks interface that all activities using this fragment must
     * implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}

