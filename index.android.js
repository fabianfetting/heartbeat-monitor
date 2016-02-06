/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  TouchableNativeFeedback
} from 'react-native';

import Button from 'react-native-button';
import { NativeModules } from 'react-native';
let MyToastAndroid = NativeModules.MyToastAndroid;

class HeartbeatMonitor extends Component {
  _handlePress() {
    console.log('button clicked')
    MyToastAndroid.show('Awesomeness!!!', MyToastAndroid.LONG);
  }
  render() {
    var TouchableElement = TouchableNativeFeedback;

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Shake or press menu button for dev menu
        </Text>
        <Button
          style={styles.button}
          onPress={this._handlePress}>
          Press Me!
        </Button>
        <TouchableElement
            onPress={() => { this._handlePress() }}
            background={TouchableNativeFeedback.SelectableBackground()}>
            <View style={{width: 200, height: 50, backgroundColor: 'red'}}>
              <Text style={{margin: 15}}>Native Feedback Button</Text>
            </View>
        </TouchableElement>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  button: {
    color: 'black',
    margin: 30,
    padding: 10,
    backgroundColor: '#dadada',
    textAlign: 'center'
  }
});

AppRegistry.registerComponent('HeartbeatMonitor', () => HeartbeatMonitor);
