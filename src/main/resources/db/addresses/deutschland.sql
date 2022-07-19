SET @country_id = UUID();
INSERT IGNORE INTO `countries`(`id`, `name`) VALUES (@country_id, 'Deutschland');

--
-- Nordrhein-Westfalen
--
SET @nordrhein_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@nordrhein_id, 'Nordrhein-Westfalen', @country_id);
--
-- Die Bezirke von Nordrhein-Westfalen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Düsseldorf', @nordrhein_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Köln', @nordrhein_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Arnsberg', @nordrhein_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Detmold', @nordrhein_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Münster', @nordrhein_id);

--
-- Berlin
--
SET @berlin_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@berlin_id, 'Bavaria', @country_id);
--
-- Die Bezirke von Berlin
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mitte', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Friedrichshain-Kreuzberg', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Pankow', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Charlottenburg-Wilmersdorf', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Spandau', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Steglitz-Zehlendorf', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Tempelhof-Schöneberg', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Neukölln', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Treptow-Köpenick', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Marzahn-Hellersdorf', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Lichtenberg', @berlin_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Reinickendorf', @berlin_id);

--
-- Bavaria
--
SET @bavaria_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@bavaria_id, 'Bavaria', @country_id);
--
-- Die Bezirke von Bavaria
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oberbayern', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Niederbayern', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oberpfalz', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oberfranken', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mittelfranken', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Unterfranken', @bavaria_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Schwaben', @bavaria_id);

--
-- Baden-Württemberg
--
SET @baden_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@baden_id, 'Baden-Württemberg', @country_id);
--
-- Die Bezirke von Baden-Württemberg
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Stuttgart', @baden_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Karlsruhe', @baden_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Freiburg', @baden_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Tübingen', @baden_id);

--
-- Brandenburg
--
SET @brandenburg_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@brandenburg_id, 'Brandenburg', @country_id);
--
-- Die Bezirke von Brandenburg
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Barnim', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Dahme-Spreewald', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Elbe-Elster', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Havelland', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Märkisch-Oderland', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oberhavel', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oberspreewald-Lausitz', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Oder-Spree', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ostprignitz-Ruppin', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Potsdam-Mittelmark', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Prignitz', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Spree-Neiße', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Teltow-Fläming', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Uckermark', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Brandenburg an der Havel', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Cottbus', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Frankfurt', @brandenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Potsdam', @brandenburg_id);

--
-- Bremen
--
SET @bremen_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@bremen_id, 'Bremen', @country_id);
--
-- Die Bezirke von Bremen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mitte', @bremen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Süd', @bremen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ost', @bremen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'West', @bremen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nord', @bremen_id);

--
-- Hamburg
--
SET @hamburg_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@hamburg_id, 'Hamburg', @country_id);
--
-- Die Bezirke von Hamburg
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Hamburg-Mitte', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Altona', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Eimsbüttel', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Hamburg-Nord', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Wandsbek', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Bergedorf', @hamburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Harburg', @hamburg_id);

--
-- Hessen
--
SET @hessen_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@hessen_id, 'Hessen', @country_id);
--
-- Die Bezirke von Hessen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Darmstadt', @hessen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Gießen', @hessen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Kassel', @hessen_id);

--
-- Niedersachsen
--
SET @niedersachsen_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@hessen_id, 'Niedersachsen', @country_id);
--
-- Die Bezirke von Niedersachsen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Braunschweig', @niedersachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Hannover', @niedersachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nord-Niedersachsen', @niedersachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Weser-Ems', @niedersachsen_id);

--
-- Mecklenburg-Vorpommern
--
SET @mecklenburg_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@mecklenburg_id, 'Mecklenburg-Vorpommern', @country_id);
--
-- Die Bezirke von Mecklenburg-Vorpommern
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Landkreis Rostock', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ludwigslust-Parchim', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mecklenburgische Seenplatte', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nordwestmecklenburg', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Vorpommern-Greifswald', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Vorpommern-Rügen', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Rostock', @mecklenburg_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Schwerin', @mecklenburg_id);

--
-- Rheinland-Pfalz
--
SET @rheinland_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@rheinland_id, 'Rheinland-Pfalz', @country_id);
--
-- Die Bezirke von Rheinland-Pfalz
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ahrweiler', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Altenkirchen (Westerwald)', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Alzey-Worms', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Bad Dürkheim', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Bad Kreuznach', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Bernkastel-Wittlich', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Birkenfeld', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Cochem-Zell', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Donnersbergkreis', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Eifelkreis Bitburg-Prüm', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Frankenthal (Pfalz)', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Germersheim', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Kaiserslautern', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Landkreis Kaiserslautern', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Koblenz', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Kusel', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Landau in der Pfalz', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ludwigshafen am Rhein', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mainz', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mainz-Bingen', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mayen-Koblenz', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Neustadt an der Weinstraße', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Neuwied', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Pirmasens', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Rhein-Hunsrück-Kreis', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Rhein-Lahn-Kreis', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Rhein-Pfalz-Kreis', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Speyer', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Südliche Weinstraße', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Südwestpfalz', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Trier', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Trier-Saarburg', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Vulkaneifel', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Westerwaldkreis', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Worms', @rheinland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Zweibrücken', @rheinland_id);

--
-- Saarland
--
SET @saarland_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@saarland_id, 'Saarland', @country_id);
--
-- Die Bezirke von Saarland
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Merzig-Wadern', @saarland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Neunkirchen', @saarland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saarbrücken', @saarland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saarlouis', @saarland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saarpfalz-Kreis', @saarland_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'St. Wendel', @saarland_id);

--
-- Sachsen
--
SET @sachsen_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@sachsen_id, 'Sachsen', @country_id);
--
-- Die Bezirke von Sachsen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Bautzen', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Chemnitz', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Dresden', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Erzgebirgskreis', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Görlitz', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Landkreis Leipzig', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Leipzig', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Meißen', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mittelsachsen', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nordsachsen', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Sächsische Schweiz-Osterzgebirge', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Vogtlandkreis', @sachsen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Zwickau', @sachsen_id);

--
-- Sachsen-Anhalt
--
SET @sachsen_anhalt_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@sachsen_anhalt_id, 'Sachsen-Anhalt', @country_id);
--
-- Die Bezirke von Sachsen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Altmarkkreis Salzwedel', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Anhalt-Bitterfeld', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Börde', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Burgenlandkreis', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Dessau-Roßlau', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Halle (Saale)', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Harz', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Jerichower Land', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Magdeburg', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Mansfeld-Südharz', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saalekreis', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Salzlandkreis', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Stendal', @sachsen_anhalt_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Wittenberg', @sachsen_anhalt_id);

--
-- Schleswig-Holstein
--
SET @schleswig_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@sachsen_id, 'Schleswig-Holstein', @country_id);
--
-- Die Bezirke von Schleswig-Holstein
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Dithmarschen', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Herzogtum Lauenburg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nordfriesland', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ostholstein', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Pinneberg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Plön', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Rendsburg-Eckernförde', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Schleswig-Flensburg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Segeberg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Steinburg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Stormarn', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Flensburg', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Lübeck', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Kiel', @schleswig_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Neumünster', @schleswig_id);

--
-- Thüringen
--
SET @thueringen_id = UUID();
INSERT IGNORE INTO `states` (`id`, `name`, `country_id`) VALUES (@thueringen_id, 'Thüringen', @country_id);
--
-- Die Bezirke von Thüringen
--
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Altenburger Land', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Eichsfeld', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Erfurt', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Gera', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Gotha', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Greiz', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Hildburghausen', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Ilm-Kreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Jena', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Kyffhäuserkreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Nordhausen', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saale-Holzland-Kreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saale-Orla-Kreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Saalfeld-Rudolstadt', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Schmalkalden-Meiningen', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Sömmerda', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Sonneberg', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Suhl', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Unstrut-Hainich-Kreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Wartburgkreis', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Weimar', @thueringen_id);
INSERT IGNORE INTO `counties` (`id`, `name`, `state_id`) VALUES (UUID(), 'Weimarer Land', @thueringen_id);