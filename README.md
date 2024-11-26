# Booster-Platform

Категории

List<Game> games = new ArrayList<>();
    List<Category> categories = new ArrayList<>();


    for (int i = 0; i < 1; i++){

      Game game = new Game();
      game.setUuid(UUID.randomUUID());
      game.setTitle("Игра " + (i + 1));

      for (int j = 0; j < 3; j++){

        Category category = new Category();
        category.setUuid((long) (Math.random() * 1000));
        category.setTitle("Катигория " + (i + 1) + "." + (j + 1));

        List<Category> categories1 = new ArrayList<>();

        for (int k = 0; k < 2; k++){

          Category category1 = new Category();
          category1.setUuid((long) (Math.random() * 1000));
          category1.setTitle("Катигория " + (i + 1) + "." + (j + 1) + "." + (k + 1));
          categories1.add(category1);



        }
        category.setCategories(categories1);
        categories.add(category);
        game.setCategories(categories);
      }

      games.add(game);
    }

    games.forEach(System.out::println);
		
		
Game{uuid=1fb3d701-4c60-44f7-a72c-1341a2d154b0, title='Игра 1', description='null', categories=
	[Category{uuid=450, title='Катигория 1.1', categories=
		[Category{uuid=239, title='Катигория 1.1.1', categories=null}, 
		Category{uuid=923, title='Катигория 1.1.2', categories=null}]},
		Category{uuid=677, title='Катигория 1.2', categories=
			[Category{uuid=889, title='Катигория 1.2.1', categories=null}, 
			Category{uuid=228, title='Катигория 1.2.2', categories=null}]}, 		
		Category{uuid=615, title='Катигория 1.3', categories=
			[Category{uuid=705, title='Катигория 1.3.1', categories=null}, 
			Category{uuid=211, title='Катигория 1.3.2', categories=null}]}]}



