# UoBAR garden app

This repository contains the whole progress of the project for unit COMS20006: Software Engineering Project.

## General information

The UoBAR app is an android mobile app which has the aim to provide the visitors of Goldney garden and Royal Fort garden an enriched experience, as close as possible to that of having a guided tour.

The main feature of the app is that it can scan QR codes. They will be placed around the art pieces in both gardens, and when scanned, they will redirect to the website page which contains the relevant information about the art piece. We have also provided an alternative to the website QR code, so that if someone want to listen to the audio instead, they can scan the corresponding QR code which will then open the specific audio file. 

An important feature of the app is that the QR codes can only be "understood" by our app. We have encoded the strings in a way that the default camera scanner from the smartphone won't be able to act on the decoded string. This means that visitors can get the enriched experience only by installing the app, which contains the audio files and knows how to deal with the links to the specific pages.

This encoding is also important for security reasons, so that if someone decides to vandalize the QR code and replace it with something entirely different, the app will decode the QR, but it won't do anything, since it is not a link specific to the website.

The app also provides general information about both gardens when clicking either the "Goldney" button or the "Royal Fort" one. 

## Stakeholders

- Developers:


As developers we have to design the app in a way that is appealing to a wide range of people, but also make it simple to continue developing and maintaining it.
- Members of the general public:
They can be broken down into several categories, the most important being:


	- Children: The app has to be designed in a way that it is also taking them into consideration, such as including audio alternatives to the text.
	- Students: The app has to provide the proper information for those who perhaps would like to do a school project based on these gardens.
	- The tech illiterate: The app has to be designed in a manner that is intuitive. It also has to be easy to use, so that even those  with little knowledge of technology can easily navigate through the menus.
	-The tech literate: For this group of people, the app has be designed using a pleasant UI, but also it has to have some features which can justify for them the necessity of the app instead of simply using the embedded scanner from the camera app. Capabilities such as audio files can be an incentive to download the app.
	
- University's IT Services:


They will be responsible for maintaining the app once we are done developing, so the app has to be maintainable. They will also be responsible with generating QR codes for the different art pieces. For them we will create a special app that generates the QR codes with the special encoding, so that they don't have to do anything extra, simply putting the name of the art piece should suffice.
- The clients:

They will want the app to be delivered on time and to have at least its basic functionality(decoding QR codes) working proeperly.

## User Stories

As a member of the general public, I want the app to be simplistic, intiutive and straightforward so that I can use it effortlessly and efficiently.

As a member of IT services I want the app to adhere to the OOP principles such as high cohesion, low coupling so that I can easily maintain it and be able to generate QR codes quickly without too much effort.

As a developer I want to develop the app as feature rich as possible, and in a simple yet efficient manner, so that I can cater for everyone's needs and make it appealing for a wide range of people.

As a client, I want the app to be delivered on time and to be bug free so that it can be released to the public.

 ## APP structure
 
 <img src=structure.jpeg>
 
 The user is given the option to choose whether they will allow the app to access their current location. If they choose to share it, then the map of the garden they are currently in is opened. If they opt not to share their location, however, the app will give them the option to manually select the garden they are currently in: 
 
 
 <img src = screenshots/Main.jpg width="250" height="500">
 
 Once the garden is selected, the map is openend, and the user's location along with the markers for each art piece is displayed. If the user presses any marker, then the link to the corresponding web page is openend in their default browser.
 
 
 <img src = screenshots/Maps.jpg width="250" height="500">

For people with visual impairment, or people who do not want to open the web page, we have implemented an audio version, which can be opened by scanning the QR code for the audio file. The user can scroll to any point in the audio file, should they feel the need to do it.

 <img src = screenshots/Audio.jpg width="250" height="500">
 
 
## Setup instruction - Android
- We recommend use Android studio to run this project which support debug and run on the real machine and also provides a device simulator  

- Install Prerequisites  
  1. JDK  
  2. Android Studio  

- Set up
  1. Clone the repository: git clone https://github.com/spe-uob/2021-UoBAR.git  
	2. Open the Android Studio main screen. Click on “Open existing android studio project”  
	3. Or go to File > Open... and choose the location where the project is located.  
	4. Android studio will start build the project and do Gradle syncronization  
  
- Run on physical device  
	1. Use USB cable connect the device to the computer(could use WIFI if supported)  
	2. On device  
		1. Open Settings app -> Developer options -> enable USB debugging  
	3. In the toolbar, select your app from the run configurations drop-down menu  
	4. select the device that you want to run your app on  
	5. Click Run  

- Run on the Android Emulator  
	1. Create an AVD  
		1. Device Manager ->select a hardware -> Next -> Select System image -> Next -> change AVD properties -> Finish  
	2. Select the AVD want to run  
  3. Run  




## Deployment Instructions - Android

For deploying the app on Google's Play Store, please visit [this link](https://orangesoft.co/blog/how-to-publish-an-android-app-on-google-play-store).

This is a step by step on how to get the APK file which can then be installed on any android device.

	1. Open the project in Android Studio by following the Set up steps 1-4
	2. In the Build section, select Build Bundle(s)/APK(s) -> Build APK(s)
	3. Android Studio will then start building an APK file
	4. APK can be then found in \app\build\outputs\apk\debug as app-debug.

The APK is now ready. It can be installed on any android phone. 

The easiest way to get it on your android smartphone is by connecting the phone to the computer, via an USB cable. When prompted with what you want to do with this device, select the option which allows you to see the files of the phone. Next, open the Files folder from your phone on your computer, and copy the app-debug there. Once you have done that, the APK should be on your phone. Click on the APK, and follow the on-screen instructions. The app should now be installed on your phone.
