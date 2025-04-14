package is.hi.hbv202g.lokaverkefni.options.translation;

public class TranslationsInitializer {
    public static void load() {
        TranslationManager.add("select_language", "Select language / Veldu tungumál:", "Veldu tungumál / Select language:");
        TranslationManager.add("english", "English", "Enska");
        TranslationManager.add("icelandic", "Icelandic", "Íslenska");

        // Game setup translations
        TranslationManager.add("welcome", "Welcome to the Game!", "Velkomin(n) í leikinn!");
        TranslationManager.add("select_theme", "Rock Paper Scissors or Pee, Poop or Toiletpaper:", "Skæri, Blað, Steinn eða Piss, Kúkur, Klósettpappír:");
        TranslationManager.add("theme_standard", "Rock, Paper, Scissors", "Skæri, Blað, Steinn");
        TranslationManager.add("theme_bathroom", "Pee, Poop, Toilet Paper", "Piss, Kúkur, Klósettpappír");
        TranslationManager.add("theme_standard_selected", "You chose Rock, Paper, Scissors", "Þú valdir Skæri, Blað, Steinn");
        TranslationManager.add("theme_bathroom_selected", "You chose Pee, Poop, Toilet Paper", "Þú valdir Piss, Kúkur, Klósettpappír");
        TranslationManager.add("select_players", "Write 1 for a one player game or 2 for a two-player game", "Skrifaðu töluna 1 fyrir 1 leikmann eða töluna 2 ef þú ert að spila við vin");
        TranslationManager.add("invalid_input", "Invalid input. Please try again", "Ógilt inntak. Vinsamlegast reyndu aftur.");

        // One player game setup
        TranslationManager.add("one_player_selected", "You have selected a one player game.\nWhat is your name?", "Þú hefur valið eins leikmanns leik.\nHvað heitir þú?");
        TranslationManager.add("select_difficulty", "Select a difficulty level:", "Veldu þér erfiðleikastig:");
        TranslationManager.add("difficulty_man", "Man (Always plays ", "Maður (Spilar alltaf ");
        TranslationManager.add("difficulty_easy", "Easy", "Auðvelt");
        TranslationManager.add("difficulty_medium", "Medium", "Miðlungs");
        TranslationManager.add("difficulty_hard", "Hard", "Erfitt");
        TranslationManager.add("difficulty_man_selected", "You have decided to play against a man. A man believes that a win is always possible with ", "Þú hefur valið að spila við mann. Menn halda að sigur fáist alltaf með ");
        TranslationManager.add("difficulty_easy_selected", "You are now playing difficulty level : Easy", "Erfiðleikastig : Maður");
        TranslationManager.add("difficulty_medium_selected", "You are now playing difficulty level : Medium", "Erfiðleikastig : Miðlungs");
        TranslationManager.add("difficulty_hard_selected", "You are now playing difficulty level : Hard", "Erfiðleikastig : Erfitt");

        // Two player game setup
        TranslationManager.add("two_player_selected", "You have selected two player game.", "Þú hefur valið að spila við vin.");
        TranslationManager.add("enter_name_player1", "Enter name for Player 1:", "Sláðu inn nafn fyrir Leikmann 1:");
        TranslationManager.add("enter_name_player2", "Enter name for Player 2:", "Sláðu inn nafn fyrir Leikmann 2:");

        // Game round
        TranslationManager.add("round", "ROUND", "UMFERÐ");
        TranslationManager.add("secret_hint", "🧑‍🔮 psst... Hate losing? Press 'u' and see what happens.", "🧑‍🔮 psst... hHatar þú að tapa? Ýttu á 'u' og sjáðu hvað gerist.");
        TranslationManager.add("choose_move", ", choose your move:", ", veldu þér hönd:");
        TranslationManager.add("you_chose", "You chose ", "Þú valdir ");
        TranslationManager.add("player_chose", " chose ", " valdi ");
        TranslationManager.add("computer_chose", "Computer chose ", "Tölvan valdi ");
        TranslationManager.add("quit_game", "You chose to quit the game.", "Þú ákvaðst að hætta í leiknum.");
        TranslationManager.add("player_quit_game", " chose to quit the game.", " ákvað að hætta í leiknum.");
        TranslationManager.add("invalid_choice", "Invalid choice. Please select a valid option.", "Ógilt val hjá þér. Vinsamlegast veldu viðeigandi valmöguleika.");
        TranslationManager.add("invalid_choice_default", "Invalid choice. Defaulting to first option.", "Ógilt val. Það er þá farið í fyrsta valmöguleikann.");
        TranslationManager.add("invalid_choice_number", "Invalid choice of a number. Please enter a number.", "Ógilt val á tölu. Vinsamlegast sláðu inn tölu.");

        // Undo and game results
        TranslationManager.add("undo_move", "Last move undone.", "Síðasta færslan er afturkölluð.");
        TranslationManager.add("undo_note", "Meow", "Mjáw");
        TranslationManager.add("undo_secret", "😉 A clean game is not always the best... This will be our little secret. 😉", "😉 Það er ekki alltaf best að segja sannleikann... Þetta verður litla leyndarmálið okkar. 😉");
        TranslationManager.add("player_wins", " WINS THIS ROUND! 🎉", " VINNUR ÞESSA UMFERÐ! 🎉");
        TranslationManager.add("computer_wins", "💻 COMPUTER WINS THIS ROUND! 💻", "💻 TÖLVAN VINNUR ÞESSA UMFERÐ! 💻");
        TranslationManager.add("draw", "🤝 IT'S A DRAW! 🤝", "🤝 ÞAÐ ER JAFNTEFLI! 🤝");

        // Difficulty increase
        TranslationManager.add("win_streak", "Impressive! You've won 5 games in a row!", "Glæsilegt! Þú hefur unnið 5 leiki í röð!");
        TranslationManager.add("increase_difficulty", "Would you like to increase the difficulty? (yes/no)", "Viltu auka erfiðleikastigið? (já/nei)");
        TranslationManager.add("difficulty_increased_easy", "Difficulty increased to Easy.", "Erfiðleikastig er hækkað í 'Auðvelt'.");
        TranslationManager.add("difficulty_increased_medium", "Difficulty increased to Medium.", "Erfiðleikastig er hækkað í 'Miðlungs'.");
        TranslationManager.add("difficulty_increased_hard", "Difficulty increased to Hard.", "Erfiðleikastig er hækkað í 'Erfitt'.");
        TranslationManager.add("max_difficulty", "You're already at the highest difficulty!", "Þú ert nú þegar á hæsta erfiðleikastigi!");

        // Continue playing
        TranslationManager.add("continue_playing", "\nYou've played ", "\nÞú hefur spilað ");
        TranslationManager.add("rounds", " rounds. Do you want to continue playing? (y/n)", " umferðir. Viltu halda áfram að spila? (já/nei)");
        TranslationManager.add("thanks_for_playing", "Thanks for playing!", "Takk fyrir að spila!");

        // Standard theme moves
        TranslationManager.add("rock", "Rock", "Steinn");
        TranslationManager.add("paper", "Paper", "Blað");
        TranslationManager.add("scissors", "Scissors", "Skæri");

        // Bathroom theme moves
        TranslationManager.add("poop", "Poop", "Kúkur");
        TranslationManager.add("toilet_paper", "Toilet Paper", "Klósettpappír");
        TranslationManager.add("pee", "Pee", "Piss");

        TranslationManager.add("invalid_name", "Name cannot be empty. Please enter at least one character.", "Nafn má ekki vera tómt. Vinsamlegast sláðu inn að minnsta kosti einn staf.");
        TranslationManager.add("rainbow", "it is a Name cannot be empty. Please enter at least one character.", "thetta erNafn má ekki vera tómt. Vinsamlegast sláðu inn að minnsta kosti einn staf.");
    }
}