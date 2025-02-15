/**
 *  Konnected Smoke Sensor
 *
 *  Copyright 2018 Konnected Inc (https://konnected.io)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
  definition (name: "Konnected Smoke Sensor", namespace: "konnected-io", author: "konnected.io", mnmn: "SmartThings", vid: "generic-smoke") {
    capability "Smoke Detector"
    capability "Sensor"
  }

  preferences {
    input (
	name: "normalState", type: "enum", title: "Normal State",
	options: ["Normally Closed", "Normally Open"],
      	defaultValue: "Normally Closed",
      	description: "By default, the alarm state is triggered when the sensor circuit is open (NC). Select Normally Open (NO) when a closed circuit indicates an alarm."
	)
  input (
      	type: "bool",
	name: "txtEnable",
	title: "Enable descriptionText logging",
	required: false,
	defaultValue: false
      )  
  }

}

def logInfo(msg) {
	if (txtEnable) {
		log.info msg
	}
}

def isClosed() {
  normalState == "Normally Open" ? "detected" : "clear"
}

def isOpen() {
  normalState == "Normally Open" ? "clear" : "detected"
}

//Update state sent from parent app
def setStatus(state) {
  def stateValue = state == "1" ? isOpen() : isClosed()
  sendEvent(name: "smoke", value: stateValue)
  def descriptionText = "$device.label is $stateValue"
  logInfo descriptionText
}
