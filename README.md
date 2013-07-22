### 13 - Android client for multiplatform, multiplayer card game
13 is a standard 52-card deck card game. For an introduction and rules please see [Wikipedia](http://en.wikipedia.org/wiki/Tien_len).

This is a complete rewrite of a [university project](https://bitbucket.org/xstherrera1987/13-from-cecs343)
which was an Android only single player game, also GPLv3.

At the moment, it is a work-in-progress that will probably see several major revisions before it is viable for serious play.  The target platform is ultimately Google App Engine.

----------------------

###Core 
contains the main game functionality.  Players, Game Rules, Gameplay, and AI
are implemented in this project.

###RMI Net
the first iteration will be a command line network game and the player lobby and gameplay setup and will be implemented in this project. this project is using Java RMI.

###Services
the second iteration will refactor the RMI Net project into generic Web Services which will be accessible to web, android, and cli clients.  The platform will be Spring MVC (or alternatively Grails).

###Webapp
the web interface will be implemented using a client-side MVC JS framework. It will be implemented using Backbone.js, or alternatively with Angular.js

###Droid
the android app will be the last project to be implemented and will most likely target Gingerbread as the minimum OS level.  Further research is necessary to determine more specific requirements.


#### misc:
Java7 is used throughout.  Services and Webapp also use Javascript and may additionally use Groovy2 (sorry Scala+Play, maybe next time)

----------------------

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
