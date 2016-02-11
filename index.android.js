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
          heart rate:
        </Text>
        <Text style={styles.heartrateValue}>
            {this.state.msg}
        </Text>
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
    margin: 30,
    marginBottom: 0
  },
  heartrateValue: {
    fontSize: 100,
    color: '#941b34',
    margin: 30,
    marginTop: 0
  }
});

AppRegistry.registerComponent('HeartbeatMonitor', () => HeartbeatMonitor);
