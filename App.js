import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  DeviceEventEmitter,
} from 'react-native';
import Subscribable from 'Subscribable';

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      msg: '–'
    };
  }
  componentDidMount() {
    Subscribable.Mixin.UNSAFE_componentWillMount.apply(this, arguments);
    this.addListenerOn(DeviceEventEmitter, 'heartrateChanged', (msg) => { this.handleHeartRateCange(msg) });
  }
  componentWillUnmount() {
    Subscribable.Mixin.componentWillUnmount.apply(this, arguments);
  }
  addListenerOn() {
    return Subscribable.Mixin.addListenerOn.apply(this, arguments);
  }
  handleHeartRateCange(msg) {
    console.log('handle heart rate', msg);
    this.setState({msg});
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
