🦖🦕 FOSSIL FRIENDS
	READ ME:

**Disclaimer:**
This is a prototype version developed to explore how to use GitHub effectively and to track my progress.
It does not refelct the final quality or content of the intended release.

Below includes a full guide for Fossil Friends:

Installation:

1. 	Unzip the folder inside the `BUILD` directory. The structure should look like this:
`
FossilFriends/

        ├── FossilFriends.jar

        ├── FossilFriendsSaves.accdb

        └── lib/

                ├── commons-lang3-3.8.1.jar

                ├── commons-logging-1.2.jar

                ├── hsqldb2.5.0.jar

                ├── jackcess-3.0.1.jar

                ├── rs2xml.jar

                └── ucanaccess-5.0.1.jar
`
3. 	Open 'FossilFriends.jar' to start the game. 
	--If you encounter prompts for which application to use, ensure that **JAVA 17.0.12** is installed.

	you can download JAVA 17 from:
	https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html


FOR MAC:
- if you encounter an error saying that Apple couldn't verify the integrity of this application follow these instructions:
	1. Ensure you have the above structure correct and have unzipped the files.
	2. Ensure that the program is not in an icloud drive.
	3. Open terminal and run these commands:
			
			cd /your_file_path
			
			xattr -d com.apple.quarantine FossilFriends.jar

- If FossilFriends.jar still isn't open, ensure you have the correct Java version.

System Requirements

	MINIMUM:
	OS		Windows 7 SP1 or newer (64-bit)
	CPU		Dual-core processor
	RAM		2GB
	STORAGE		10MB free disk space
	JAVA VERSION	17.0.12


===============================
=      Notes & Warnings       =
===============================

- Do not rename or remove any files in the `lib` folder.
- All save data is stored in the `FossilFriendsSaves.accdb` file. Deleting this file will erase all progress.'

===============================
=     Contact & Feedback      =
===============================

For bugs or feedback, reach out via GitHub Issues or email: damian.nell2203@gmail.com
