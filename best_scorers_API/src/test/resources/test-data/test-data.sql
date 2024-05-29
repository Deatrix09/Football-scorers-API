INSERT INTO public.country (id,country_name) VALUES (1,'Spain');
INSERT INTO public.country (id,country_name) VALUES (2,'Italy');
INSERT INTO public.country (id,country_name) VALUES (3,'Germany');
INSERT INTO public.country (id,country_name) VALUES (6,'France');


INSERT INTO  public.league (id,league_name, country_id) VALUES (1,'La Liga', 1);
INSERT INTO  public.league (id,league_name, country_id) VALUES (2,'Serie A', 2);
INSERT INTO  public.league (id,league_name, country_id) VALUES (3,'Bundesliga', 3);
INSERT INTO  public.league (id,league_name, country_id) VALUES (4,'Ligue 1', 6);

INSERT INTO  public.club (id,club_code, league_id) VALUES
(1,'Real Betis', 1),
(2,'FC Barcelona', 1),
(3,'Real Madrid', 1);
INSERT INTO  public.club (id,club_code, league_id) VALUES
(4,'Paris Saint-Germain FC', 4),
(5,'Hertha BSC', 3);
INSERT INTO  public.club (id,club_code, league_id) VALUES
(6,'TSG 1899 Hoffenheim', 3),
(7,'Borussia Monchengladbach', 3);

INSERT INTO  public.player (id,full_name, matches_played, count_of_substitutions, minutes_played, goals_scored, expected_goals, expected_goals_per_match_avg, shots, shots_on_target, shots_per_match_avg, shots_on_target_per_match_avg, year, club_id) VALUES
(1,'Juanmi Callejon', 19, 16, 1849, 11, 6.62, 0.34, 48, 20, 2.47, 1.03, 2016, 1),
(2,'Antoine Griezmann', 36, 0, 3129, 16, 11.86, 0.36, 88, 41, 2.67, 1.24, 2016, 2),
(3,'Karim Benzema', 23, 6, 1967, 11, 13.25, 0.64, 69, 34, 3.33, 1.64, 2016, 3);
INSERT INTO  public.player (id,full_name, matches_played, count_of_substitutions, minutes_played, goals_scored, expected_goals, expected_goals_per_match_avg, shots, shots_on_target, shots_per_match_avg, shots_on_target_per_match_avg, year, club_id) VALUES
(4,'Andrej Kramaric', 28, 2, 2527, 16, 19.42, 0.73, 110, 43, 4.14, 1.62, 2018, 6),
(5,'Alassane Plea', 28, 6, 2613, 12, 11.28, 0.41, 83, 41, 3.02, 1.49, 2018, 7);



