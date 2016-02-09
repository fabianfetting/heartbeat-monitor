# Initial Build Process

```
set -xg ANDROID_SERIAL "emulator-5554"
react-native run-android
adb uninstall com.heartbeatmonitor
cd android & gradle :app:installDebug
adb shell am start -n com.heartbeatmonitor/.MainActivity

adb -s [DEVICE_ID] reverse tcp:8081 tcp:8081
```


## Bluetooth Debugging

```
adb -s [DEVICE_ID] forward --remove tcp:4444
adb -s [DEVICE_ID] forward tcp:4444 localabstract:/adb-hub
adb -s [DEVICE_ID] connect 127.0.0.1:4444
```


## Connect android wear with android smartphone emulator

http://stackoverflow.com/questions/25205888/pairing-android-and-wear-emulators

```
telnet localhost 5556
# redir del tcp:5601
redir add tcp:5601:5601
```

- click smartwatch icon inside android wear app
