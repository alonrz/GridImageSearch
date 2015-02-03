# Google Image Search App for Android
This app is designed to search for images using Google's image search engine. It can search for strings and add filters (color, type, size, site) to the search. You can view an image and share with friends all from your phone. The focus for this project is getting a handle on implicit and explicit intents, ActionBar and using external APIs. 

Time spend: ~12 hours. 

External libraries used:
- [Android Asynchronous Http Client](http://loopj.com/android-async-http/)
- [Picasso](http://square.github.io/picasso/)
- [AndroidStaggeredGrid](https://github.com/f-barth/AndroidStaggeredGrid)
- [TouchImageView](https://github.com/MikeOrtiz/TouchImageView)

User stories (must):
 * [x] User can enter a search query that will display a grid of image results from the Google Image API.
 * [x] User can click on "settings" icon which allows selection of advanced search options to filter results
 * [x] User can configure advanced search filters such as:
  - Size (small, medium, large, extra-large)
  - Color filter (black, blue, brown, gray, green, etc...)
  - Type (faces, photo, clip art, line art)
  - Site (espn.com)
 * [x] Subsequent searches will have any filters applied to the search results
 * [x] User can tap on any image in results to see the image full-screen
 * [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages) 

User stories (Optional):
 * [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
 * [x] Advanced: User can share an image to their friends or email it to themselves
 * [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring (e.g. background color, and border lines were added as well as icons for settings, share, and place holders for images. Experimented with changing themes to custom themes)
 * [x] Bonus: Use the StaggeredGridView to display improve the grid of image results
 * [x] Bonus: User can zoom or pan images displayed in full-screen detail view
 * [x] Extra: User can fo another search from the image full-screen view
 * [x] Extra: Home button to return to previous window

 


![Video Walkthrough](GridImageSearchAppWalkthough.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
