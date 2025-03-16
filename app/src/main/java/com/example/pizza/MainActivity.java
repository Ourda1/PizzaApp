package com.example.pizza;

import static kotlin.text.Typography.half;
import static kotlin.text.Typography.pound;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pizza.adapter.PizzaAdapter;
import com.example.pizza.classes.Produit;
import com.example.pizza.service.ProduitService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ProduitService ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView shareButton = findViewById(R.id.shareButton);

        ps = ProduitService.getInstance();
        initData();

        listView = findViewById(R.id.liste);
        PizzaAdapter adapter = new PizzaAdapter(ps.findAll(),this);
        listView.setAdapter(adapter);

        // Gestionnaire d'événements pour les clics sur la liste
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produit selectedProduit = (Produit) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, PizzaDetailActivity.class);
                intent.putExtra("PIZZA_ID", selectedProduit.getId());
                startActivity(intent);

            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Découvrez ces délicieuses recettes de pizza ! 🍕";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> resInfoList = packageManager.queryIntentActivities(shareIntent, 0);

                // Liste des intents filtrés (on exclut WhatsApp ici)
                List<Intent> filteredIntents = new ArrayList<>();

                if (resInfoList != null) {
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;

                        // Si l'application n'est pas WhatsApp, on l'ajoute à la liste filtrée
                        if (!packageName.contains("com.whatsapp")) {
                            Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                            targetedShareIntent.setType("text/plain");
                            targetedShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                            targetedShareIntent.setPackage(packageName);
                            filteredIntents.add(targetedShareIntent);
                        }
                    }
                }

                if (!filteredIntents.isEmpty()) {
                    Intent chooserIntent = Intent.createChooser(filteredIntents.remove(0), "Partager via");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, filteredIntents.toArray(new Intent[0]));
                    startActivity(chooserIntent);
                }
            }
        });
    }

    private void initData() {
        ps.create(new Produit(
                "BARBECUED CHICKEN PIZZA",
                3,
                R.mipmap.pizza1,
                "35 min",
                "- 2 boneless skinless chicken breast halves (6 ounces each)\n" +
                        "- 1/4 teaspoon pepper\n" +
                        "- 1 cup barbecue sauce, divided\n" +
                        "- 1 tube (13.8 ounces) refrigerated pizza crust\n" +
                        "- 2 teaspoons olive oil\n" +
                        "- 2 cups shredded Gouda cheese\n" +
                        "- 1 small red onion, halved and thinly sliced\n" +
                        "- 1/4 cup minced fresh cilantro",

                "So fast and so easy with refrigerated pizza crust, these saucy, smoky pizzas make quick fans with their hot-off-the-grill, rustic flavor. They're perfect for spur-of-the-moment cookouts and summer dinners on the patio. —Alicia Trevithick, Temecula, California",

                "STEP 1:\n\nSprinkle chicken with pepper; place on an oiled grill rack over medium heat. Grill, covered, until a thermometer reads 165°, 5-7 minutes per side, basting frequently with 1/2 cup barbecue sauce during the last 4 minutes. Cool slightly. Cut into cubes.\n\n" +
                        "STEP 2:\n\nDivide dough in half. On a well greased large sheet of heavy-duty foil, press each portion of dough into a 10x8-in. rectangle; brush lightly with oil. Invert dough onto grill rack; peel off foil. Grill, covered, over medium heat until bottom is lightly browned, 1-2 minutes.\n\n" +
                        "STEP 3:\n\nRemove from grill. Spread grilled sides with remaining barbecue sauce. Top with cheese, chicken and onion. Grill, covered, until bottom is lightly browned and cheese is melted, 2-3 minutes. Sprinkle with cilantro. Yield: 2 pizzas (4 pieces each)."
        ));
        // Création de la première pizza (BRUSCHETTA PIZZA)
        ps.create(new Produit(
                "BRUSCHETTA PIZZA",
                5,
                R.mipmap.pizza2,
                "35 min",
                "- 1/2 pound reduced-fat bulk pork sausage\n" +
                        "- 1 prebaked 12-inch pizza crust\n" +
                        "- 1 package (6 ounces) sliced turkey pepperoni\n" +
                        "- 2 cups shredded part-skim mozzarella cheese\n" +
                        "- 1-1/2 cups chopped plum tomatoes\n" +
                        "- 1/2 cup fresh basil leaves, thinly sliced\n" +
                        "- 1 tablespoon olive oil\n" +
                        "- 2 garlic cloves, minced\n" +
                        "- 1/2 teaspoon minced fresh thyme or 1/8 teaspoon dried thyme\n" +
                        "- 1/2 teaspoon balsamic vinegar\n" +
                        "- 1/4 teaspoon salt\n" +
                        "- 1/8 teaspoon pepper\n" +
                        "- Additional fresh basil leaves, optional",

                "You might need a knife and fork for this hearty pizza! Loaded with Italian flavor and plenty of fresh tomatoes, it's bound to become a family favorite. It's even better with a homemade, whole wheat crust! —Debra Kell, Owasso, Oklahoma",

                "STEP 1:\n\nIn a small skillet, cook sausage over medium heat until no longer pink; drain. Place crust on an ungreased baking sheet. Top with pepperoni, sausage and cheese. Bake at 450° for 10-12 minutes or until cheese is melted.\n\n" +
                        "STEP 2:\n\nIn a small bowl, combine the tomatoes, sliced basil, oil, garlic, thyme, vinegar, salt and pepper. Spoon over pizza. Garnish with additional basil if desired. Yield: 8 slices."
        ));

        // Création de la deuxième pizza (SPINACH PIZZA)
        ps.create(new Produit(
                "SPINACH PIZZA",
                2,
                R.mipmap.pizza3,
                "25 min",
                "- 1 package (6-1/2 ounces) pizza crust mix\n" +
                        "- 1/2 cup Alfredo sauce\n" +
                        "- 2 medium tomatoes\n" +
                        "- 4 cups chopped fresh spinach\n" +
                        "- 2 cups shredded Italian cheese blend",

                "This tasty pizza is so easy to prepare. My family, including my young daughter, loves it. What an easy way to make a delicious, veggie filled meal! —Dawn Bartholomew, Raleigh, North Carolina",

                "STEP 1:\n\nPrepare pizza dough according to package directions. With floured hands, press dough onto a greased 12-in. pizza pan.\n\n" +
                        "STEP 2:\n\nSpread Alfredo sauce over dough to within 1 in. of edges. Thinly slice or chop tomatoes; top pizza with spinach, tomatoes and cheese.\n\n" +
                        "STEP 3:\n\nBake at 450° for 10-15 minutes or until cheese is melted and crust is golden brown. Yield: 4-6 servings."
        ));
        ps.create(new Produit(
                "DEEP-DISH SAUSAGE PIZZA",
                8,
                R.mipmap.pizza4,
                "45 min",
                "- 1 package (1/4 ounce) active dry yeast\n" +
                        "- 2/3 cup warm water (110° to 115°)\n" +
                        "- 1-3/4 to 2 cups all-purpose flour\n" +
                        "- 1/4 cup vegetable oil\n" +
                        "- 1 teaspoon each dried oregano, basil and marjoram\n" +
                        "- 1/2 teaspoon garlic salt\n" +
                        "- 1/2 teaspoon onion salt\n",
                "My Grandma made the tastiest snacks for us when we stayed the night at her farm. Her wonderful pizza, hot from the oven, was covered with cheese and had fragrant herbs in the crust. Now this pizza is frequently a meal for my husband and me and our two young daughters. —Michele Madden, Washington Court House, Ohio",
                "STEP 1:\n\nIn a mixing bowl, dissolve yeast in water. Add 1 cup flour, oil and seasonings; beat until smooth. Add enough remaining flour to form a soft dough. Turn onto a floured surface; knead until smooth and elastic, 6-8 minutes. Place in a greased bowl; turn once to grease top. Cover and let rise in a warm place until doubled, about 1 hour. Punch dough down; roll out into a 15-in. circle. Transfer to a well-greased 12-in. heavy ovenproof skillet, letting dough drape over edges. Sprinkle with 1 cup mozzarella.\n\n" +
                        "STEP 2:\n\nIn another skillet, saute onion, green peppers and seasonings in oil until tender; drain. Layer half of the mixture over crust. Layer with half of the Parmesan, sausage and tomatoes. Sprinkle with 2 cups mozzarella. Repeat layers. Fold crust over to form an edge. Bake at 400° for 20 minutes. Sprinkle with pepperoni and remaining mozzarella. Bake 10-15 minutes longer or until crust is browned. Let stand 10 minutes before slicing. Yield: 8 slices."
        ));

        ps.create(new Produit(
                "HOMEMADE PIZZA",
                4,
                R.mipmap.pizza5,
                "50 min",
                "- 1 package (1/4 ounce) active dry yeast\n" +
                        "- 1 teaspoon sugar\n" +
                        "- 1-1/4 cups warm water (110° to 115°)\n" +
                        "- 1/4 cup canola oil\n" +
                        "- 1 teaspoon salt\n" +
                        "- 3-1/2 cups all-purpose flour\n" +
                        "- 1/2 pound ground beef\n" +
                        "- 1 small onion, chopped\n" +
                        "- 1 can (15 ounces) tomato sauce\n" +
                        "- 1 teaspoon dried basil\n" +
                        "- 1 medium green pepper, diced\n" +
                        "- 2 cups shredded part-skim mozzarella cheese\n",
                "This recipe is a hearty, zesty main dish with a crisp, golden crust. Feel free to use whatever toppings your family enjoys on these homemade pizzas. —Marianne Edwards, Lake Stevens, Washington",
                "STEP 1:\n\nIn large bowl, dissolve yeast and sugar in water; let stand for 5 minutes. Add oil and salt. Stir in flour, a cup at a time, until a soft dough forms.\n\n" +
                        "STEP 2:\n\nTurn onto floured surface; knead until smooth and elastic, about 2-3 minutes. Place in a greased bowl, turning once to grease the top. Cover and let rise in a warm place until doubled, about 45 minutes. Meanwhile, cook beef and onion over medium heat until no longer pink; drain.\n\n" +
                        "STEP 3:\n\nPunch down dough; divide in half. Press each into a greased 12-in. pizza pan. Combine the tomato sauce, oregano and basil; spread over each crust. Top with beef mixture, green pepper and cheese.\n\n" +
                        "STEP 4:\n\nBake at 400° for 25-30 minutes or until crust is lightly browned. Yield: 2 pizzas (3 servings each)."
        ));

        ps.create(new Produit(
                "PESTO CHICKEN PIZZA",
                3,
                R.mipmap.pizza6,
                "50 min",
                "- 2 teaspoons active dry yeast\n" +
                        "- 1 cup warm water (110° to 115° F)\n" +
                        "- 2 3/4 cups bread flour\n" +
                        "- 1 tablespoon plus 2 teaspoons olive oil (divided)\n" +
                        "- 1 tablespoon sugar\n" +
                        "- 1 1/2 teaspoons salt (divided)\n" +
                        "- 1/2 pound boneless, skinless chicken breasts (cut into 1/2-inch pieces)\n" +
                        "- 1 small onion (halved and thinly sliced)\n" +
                        "- 1/2 each of small green, red, and yellow peppers (julienned)\n" +
                        "- 1/2 cup sliced fresh mushrooms\n" +
                        "- 3 tablespoons prepared pesto\n" +
                        "- 1 1/2 cups (6 ounces) shredded part-skim mozzarella cheese\n" +
                        "- 1/4 teaspoon pepper",
                "This is the only pizza I make now. We love it! Keeping the spices simple helps the flavors of the chicken and vegetables come through. The pizza tastes incredible and is good for you, too. —Heather Thompson, Woodland Hills, California",
                "STEP 1:\n" +
                        "In a large bowl, dissolve yeast in warm water. Beat in 1 cup flour, 1 tablespoon oil, sugar, and 1 teaspoon salt. Add the remaining flour; beat until combined.\n\n" +
                        "STEP 2:\n" +
                        "Turn onto a lightly floured surface; knead until smooth and elastic, about 6-8 minutes. Place in a bowl coated with cooking spray, turning once to coat top. Cover and let rise in a warm place until doubled, about 1 hour.\n\n" +
                        "STEP 3:\n" +
                        "In a large nonstick skillet over medium heat, cook the chicken, onion, peppers, and mushrooms in the remaining oil until the chicken is no longer pink and vegetables are tender. Remove from heat and set aside.\n\n" +
                        "STEP 4:\n" +
                        "Punch dough down; roll into a 15-inch circle. Transfer to a 14-inch pizza pan. Build up edges slightly. Spread with pesto. Top with chicken mixture and cheese. Sprinkle with pepper and remaining salt.\n\n" +
                        "STEP 5:\n" +
                        "Bake at 400°F for 18-20 minutes or until crust and cheese are lightly browned.\n\n" +
                        "STEP 6 (Freeze option):\n" +
                        "Bake pizza crust as directed; cool. Top with all the ingredients as directed and securely wrap and freeze the unbaked pizza. To use, unwrap pizza; bake as directed, increasing time as necessary. Yield: 8 slices."
        ));
        ps.create(new Produit(
                "LOADED MEXICAN PIZZA",
                3,
                R.mipmap.pizza7,
                "30 min",
                "- 1 can (15 ounces) black beans, rinsed and drained\n" +
                        "- 1 medium red onion, chopped\n" +
                        "- 1 small sweet yellow pepper, chopped\n" +
                        "- 3 teaspoons chili powder\n" +
                        "- 3/4 teaspoon ground cumin\n" +
                        "- 3 medium tomatoes, chopped\n" +
                        "- 1 jalapeno pepper, seeded and finely chopped\n" +
                        "- 1 garlic clove, minced\n" +
                        "- 1 prebaked 12-inch thin pizza crust\n" +
                        "- 2 cups chopped fresh spinach\n" +
                        "- 2 tablespoons minced fresh cilantro\n" +
                        "- Hot pepper sauce to taste\n" +
                        "- 1/2 cup shredded reduced-fat cheddar cheese\n" +
                        "- 1/2 cup shredded pepper jack cheese",

                "My husband is a picky eater, but this healthy pizza has lots of flavor, and he actually looks forward to it. Leftovers are no problem, because this meal tastes better the next day. —Mary Barker, Knoxville, Tennessee",

                "STEP 1:\n" +
                        "In a small bowl, mash black beans. Stir in the onion, yellow pepper, chili powder, and cumin. In another bowl, combine the tomatoes, jalapeno, and garlic.\n\n" +

                        "STEP 2:\n" +
                        "Place crust on an ungreased 12-inch pizza pan; spread with bean mixture. Top with tomato mixture and spinach. Sprinkle with cilantro, pepper sauce, and cheeses.\n\n" +

                        "STEP 3:\n" +
                        "Bake at 400°F for 12-15 minutes or until cheese is melted. Yield: 6 slices."
        ));

        ps.create(new Produit(
                "BACON CHEESEBURGER PIZZA",
                2,
                R.mipmap.pizza8,
                "20 min",
                "- 1/2 pound ground beef\n" +
                        "- 1 small onion, chopped\n" +
                        "- 1 prebaked Italian bread shell crust (1 pound)\n" +
                        "- 1 can (8 ounces) pizza sauce\n" +
                        "- 6 bacon strips, cooked and crumbled\n" +
                        "- 20 dill pickle coin slices\n" +
                        "- 2 cups (8 ounces) shredded mozzarella cheese\n" +
                        "- 2 cups (8 ounces) shredded cheddar cheese\n" +
                        "- 1 teaspoon pizza or Italian seasoning",

                "Kids of all ages love pizza and cheeseburgers, and this recipe combines them both. My grandchildren usually request pizza for supper when they visit me. They like to help me assemble this version, and they especially enjoy eating it! —Cherie Ackerman, Lakeland, Minnesota",

                "STEP 1:\n" +
                        "In a skillet, cook beef and onion over medium heat until meat is no longer pink; drain and set aside.\n\n" +

                        "STEP 2:\n" +
                        "Place crust on an ungreased 12-inch pizza pan. Spread with pizza sauce. Top with beef mixture, bacon, pickles, and cheeses. Sprinkle with pizza seasoning. Bake at 450°F for 8-10 minutes or until cheese is melted. Yield: 8 slices."
        ));

        ps.create(new Produit(
                "PIZZA MARGHERITA",
                1,
                R.mipmap.pizza9,
                "30 min",
                "- 3 teaspoons active dry yeast\n" +
                        "- 1 cup warm water (110° to 115° F)\n" +
                        "- 2 tablespoons olive oil\n" +
                        "- 1 teaspoon sugar\n" +
                        "- 1 teaspoon salt\n" +
                        "- 3 cups bread flour",

                "A classic Pizza Margherita, named for Queen Margherita of Italy, shows off the colors of the Italian flag with red tomatoes, white mozzarella and fresh green basil. It's so scrumptious that you'll be glad the recipe makes not one but two 13-inch pizzas! —Loretta Lawrence, Myrtle Beach, South Carolina",

                "STEP 1:\n" +
                        "In a large mixing bowl, dissolve yeast in warm water. Add the oil, sugar, salt, and 1 cup flour. Beat until smooth. Stir in enough remaining flour to form a soft dough.\n\n" +

                        "STEP 2:\n" +
                        "Turn onto a floured surface; knead until smooth and elastic, about 6-8 minutes. Place in a bowl coated with cooking spray, turning once to coat the top. Cover and let rise in a warm place until doubled, about 1 hour.\n\n" +

                        "STEP 3:\n" +
                        "Punch dough down; divide in half. Roll each portion into a 13-inch circle. Transfer to two 14-inch pizza pans coated with cooking spray; build up edges slightly. Cover and let rest for 10 minutes.\n\n" +

                        "STEP 4:\n" +
                        "Spoon tomatoes over crusts. Top with basil, cheese, oregano, pepper flakes, salt, and pepper. Drizzle with oil. Bake at 450°F for 15-20 minutes or until crust and cheese are golden brown. Yield: 2 pizzas (8 slices each)."
        ));

        ps.create(new Produit(
                "PEPPERONI-SAUSAGE STUFFED PIZZA",
                5,
                R.mipmap.pizza10,
                "45 min",
                "- 1 package (1/4 ounce) active dry yeast\n" +
                        "- 1-1/4 cups warm water (110° to 115° F)\n" +
                        "- 2 tablespoons olive oil\n" +
                        "- 1-1/2 teaspoons salt\n" +
                        "- 1 teaspoon sugar\n" +
                        "- 3-1/2 to 4 cups all-purpose flour",

                "For 30 years, friends have been telling me to open a pizzeria using this recipe. It even freezes well. —Elizabeth Wolff, Carmel, Indiana",

                "STEP 1:\n" +
                        "In a small bowl, dissolve yeast in warm water. In a large bowl, combine oil, salt, sugar, yeast mixture, and 1 cup flour; beat on medium speed until smooth. Stir in enough remaining flour to form a stiff dough.\n\n" +

                        "STEP 2:\n" +
                        "Turn dough onto a floured surface; knead until smooth and elastic, about 6-8 minutes. Place in a greased bowl, turning once to grease the top. Cover with plastic wrap and let rise in a warm place until doubled, about 1 hour.\n\n" +

                        "STEP 3:\n" +
                        "Preheat oven to 425°F. Grease a 13x9-inch baking pan. Punch down dough; divide into three portions. On a lightly floured surface, combine two portions of dough and roll into a 15x11-inch rectangle. Transfer to prepared pan, pressing onto bottom and up sides of pan. Top with 2 cups mozzarella cheese, 2 cups cheddar cheese. Sprinkle with flour, seasonings, cooked sausage, mushrooms, and pepperoni.\n\n" +

                        "STEP 4:\n" +
                        "Roll out remaining dough into a 13x9-inch rectangle. Place dough over filling, crimping edges to seal; prick top with a fork. Sprinkle with remaining cheeses. Bake on a lower oven rack for 10 minutes.\n\n" +

                        "STEP 5:\n" +
                        "Reduce oven setting to 375°F. Spread pizza sauce over cheese. Bake 30-35 minutes longer or until edges are lightly browned. Let stand for 10 minutes before cutting. If desired, sprinkle with Parmesan cheese. Yield: 12 servings."
        ));

        ps.create(new Produit("PESTO CHICKEN PIZZA",3,R.mipmap.pizza6,"50 min","- 2 teaspoons active dry yeast\n- 1 cup warm water (110° to 115°)\n- 2-3/4 cups bread flour\n- 1 tablespoon plus 2 teaspoons olive oil, divided\n- 1 tablespoon sugar\n- 1-1/2 teaspoons salt, divided\n- 1/2 pound boneless skinless chicken breasts, cut into 1/2-inch pieces\n- 1 small onion, halved and thinly sliced\n- 1/2 each small green, sweet red and yellow peppers, julienned\n- 1/2 cup sliced fresh mushrooms\n- 3 tablespoons prepared pesto\n- 1-1/2 cups (6 ounces) shredded part-skim mozzarella cheese\n- 1/4 teaspoon pepper","This is the only pizza I make now. We love it! Keeping the spices simple helps the flavors of the chicken and vegetables come through. The pizza tastes incredible and is good for you, too. —Heather Thompson, Woodland Hills, California","STEP 1:\n\n  In a large bowl, dissolve yeast in warm water. Beat in 1 cup flour, 1 tablespoon oil, sugar and 1 teaspoon salt. Add the remaining flour; beat until combined.\n\nSTEP 2:\n\n  Turn onto a lightly floured surface; knead until smooth and elastic, about 6-8 minutes. Place in a bowl coated with cooking spray, turning once to coat top. Cover and let rise in a warm place until doubled, about 1 hour.\n\nSTEP 3:\n\n  In a large nonstick skillet over medium heat, cook the chicken, onion, peppers and mushrooms in remaining oil until chicken is no longer pink and vegetables are tender. Remove from the heat; set aside.\n\nSTEP 4:\n\n  Punch dough down; roll into a 15-in. circle. Transfer to a 14-in. pizza pan. Build up edges slightly. Spread with pesto. Top with chicken mixture and cheese. Sprinkle with pepper and remaining salt.\n\nSTEP 5:\n\n  Bake at 400° for 18-20 minutes or until crust and cheese are lightly browned.\n\nSTEP 6:\n\n  Freeze option: Bake pizza crust as directed; cool. Top with all the ingredients as directed and securely wrap and freeze unbaked pizza. To use, unwrap pizza; bake as directed, increasing time as necessary. Yield: 8 slices."));


    }
}