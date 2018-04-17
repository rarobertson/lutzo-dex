package max.com.lutzodex;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.InputStream;

import static java.lang.System.exit;

/**
 * Created on 27/07/2015.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Especies especies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        especies = new Especies(this.getResources());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new InicialActivity())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.exit);
            //alertDialogBuilder.setMessage(R.string.exit);
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            exit(0);
                        }
                    });

            alertDialogBuilder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pesquisar_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, SearchActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_estruturas_morfologicas) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, EstruturasActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_possiveis_especies_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, ListaActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_resetar_busca) {
            InputStream file = this.getResources().openRawResource(R.raw.especies);
            especies = new Especies(this.getResources());
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, SearchActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_respostas_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, AsksActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_sobre_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, AboutActivity.getInstance(id + 1))
                    .commit();
        } else if (id == R.id.nav_referencia_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, ReferencesActivity.getInstance(id + 1))
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
