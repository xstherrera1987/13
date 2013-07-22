### High Priority
+ *[rminet/client]*, *[core/gameplay]* implement single player game
+ *[rminet/client]* refactor GameConfigurator logic and i/o apart in order to reuse logic in droid and webapp

### Medium Priority
+ *[core]* player authentication can be simply their IP address hashed against a magic string.
+ + investigate crypto/hash algorithm implementation common to android/web/java environments.
+ + + need not be fast because it will be calculated only once

### Low Priority
+ *[core]* use real usernames as a basis for AI fake names
+ *[core]* use EnumSet instead of Card[]
+ + required for Android
+ setup continuous integration server (Jenkins)
+ *[core]* replace disconnected human players with AIs
+ *[droid]* (re)implement droid client
+ *[droid]* create a test project for droid project
+ use JCE [BouncyCastle](http://www.bouncycastle.org/java.html) crypto algorithms to produce Token.
+ use SSL for communication.