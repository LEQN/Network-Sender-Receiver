# Network-Sender-Receiver

A simple Network Sender and Receiver which takes a message and splits it into data packets to be transmitted and reads data packets, reassembling those of a specific packet ID.

#### SenderMain takes 3 command-line arguements:
1. Maximum transmission unit (mtu), must be greater than 0 and no more than max. Must be Larger than ID digit count +7 to accomodate the ID e.g. an ID 12345 requires a minimum MTU of 13.
2. ID, the ID that will be assigned to the packets of the split message being sent. Must be greater than 0 and no more than max.
3. Message to be sent, must contain at least a singel character.

Outut will be displayed in the format:   
12345,F,0  ,H   
12345,F,1  ,e   
12345,F,2  ,l   
12345,F,3  ,l   
12345,T,4  ,o 


#### ReceiverMain takes a single command-line arguement:
1. The ID of packets to be reassembled.

 
It then Reads input in the format of the sender output packets and reassembles the packets with matching ID in the correct order using the packet number. It checks that all packets have arrived, as long as all packets are correctly received it ignores duplicates or packets of different ID. If packets are missing or the format is incoorect it will output an error.
