# Initial Build Process

```
set -xg ANDROID_SERIAL "emulator-5554"
react-native run-android
adb uninstall com.heartbeatmonitor
cd android & gradle :app:installDebug
adb shell am start -n com.heartbeatmonitor/.MainActivity
```


## Bluetooth Debugging

```
adb forward --remove tcp:4444
adb forward tcp:4444 localabstract:/adb-hub
adb connect localhost:4444
```


## Connect android wear with android smartphone emulator

http://stackoverflow.com/questions/25205888/pairing-android-and-wear-emulators

```
telnet localhost 5556
# redir del tcp:5601
redir add tcp:5601:5601
```

- click smartwatch icon inside android wear app
