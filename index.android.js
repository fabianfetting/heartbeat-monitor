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
  TouchableNativeFeedback,
  DeviceEventEmitter
} from 'react-native';
import Subscribable from 'Subscribable';

class HeartbeatMonitor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      msg: '–'
    };
  }
  addListenerOn() {
    return Subscribable.Mixin.addListenerOn.apply(this, arguments);
  }
  componentWillMount() {
    Subscribable.Mixin.componentWillMount.apply(this, arguments);
  }
  _handlePress() {
    console.log('BUTTON:', 'Hello World!');
  }
  _handleHeartRateCange(msg) {
    console.log('handle heart rate', msg);
    this.setState({msg});
  }
  componentDidMount() {
      this.addListenerOn(DeviceEventEmitter,
                         'heartrateChanged',
                         (msg) => { this._handleHeartRateCange(msg) });
  }
  componentWillUnmount() {
    Subscribable.Mixin.componentWillUnmount.apply(this, arguments);
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.heartrate}>
          Wear heartrate: {this.state.msg}
        </Text>
        <TouchableNativeFeedback
          onPress={() => { this._handlePress() }}
          background={TouchableNativeFeedback.SelectableBackgroundBorderless()}>
          <View style={styles.buttonView}>
            <Text style={styles.buttonText}>Hello World!</Text>
          </View>
        </TouchableNativeFeedback>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF'
  },
  heartrate: {
    fontSize: 25,
    textAlign: 'center',
    margin: 30
  },
  buttonView: {
    backgroundColor: '#757575',
    borderRadius: 10,
    borderColor: '#000',
    borderWidth: 2,
    padding: 15
  },
  buttonText: {
    color: '#fff',
    fontSize: 25
  }
});

AppRegistry.registerComponent('HeartbeatMonitor', () => HeartbeatMonitor);
