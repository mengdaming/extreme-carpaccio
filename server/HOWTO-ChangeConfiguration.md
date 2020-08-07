# Hints for changing settings in `configuration.json`

__Notes__


- Changes are taken into account as soon as the file `configuration.json` is saved. There is no need to
re-start the server!
- Beware that re-starting the server resets all
counters, including registrations. This means that
if you do it all history will be lost, and participants will have to register again
- To start the server:
  ```sh
  DEBUG=xcarpaccio:server npm start
  ```
- By default extreme carpaccio server is listening
on port 3000

## Turning on/off order dispatching

When starting the server, it's dispatching order
by default:

```json
  "active": true,
```

To turn it off:

```json
  "active": false,
```

## Turning on/off cash freeze

To stop players from accumulating cash

```json
  "cashFreeze": true,
```

To (re)enable players to accumulate cash

```json
  "cashFreeze": false,
```

## Changing the reduction plan

Replace the following line

```json
  "reduction": "STANDARD",
```

With one of the following (names are self-explanatory)

```json
  "reduction": "HALF PRICE",
```

```json
  "reduction": "PAY THE PRICE",
```

## Adding off-line Penalty

Players may get a penalty when their server
is off line.

By default they don't:

```json
  "offlinePenalty": 0,
```

To add an off-line penalty of $50

```json
  "offlinePenalty": 50,
```

## Changing tax rates for specific countries

An example is provided in  the file `configuration.json` for Slovakia (note that this
example does not alter Slovakia's default tax rate of 18%)

```json
"taxes": {
  "SK": "function(price) { if(price>2000) return price * 1.18; else return price * 1.18; }"
}
```

These are functions, so any kind of tax plan can be
added.

Below example changes the tax rate for Germany to
0% for any price below 1000, and to 30% for any price
above.

```json
"taxes": {
  "DE": "function(price) { if(price>1000) return price * 1.30; else return price; }"
}
```

## Inserting bad requests

Bad requests can be sent periodically by the server.

This can be tuned by changing the below section in `configuration.json`:

```json
  "badRequest": {
    "active": false,
    "period": 5,
    "modes": [0,1,2,3,4,5,6,7,8,9,10]
  },
```

### Turning on/off bad requests

Bad requests are off by default:

```json
    "active": false,
```

To turn them on:

```json
    "active": true,
```

### Adjusting bad requests frequency

Change the value below to adjust the occurrence
rate of bad requests. A period of 5 means that one
bad request will be sent every 5 requests

```json
    "period": 5,
```

### Selecting types of bad requests

Different kinds of bad requests can be sent by the server. You can choose the ones that should be sent
by setting the list of modes:

```json
    "modes": [0,1,2,3,4,5,6,7,8,9,10]
```

Here's the meaning for each mode value:

| Mode | Meaning | Example of values sent |
| ---- | ----- | --- |
| 0    | Sends an empty order | `{}` |
| 1    | Sends non-json request | `[true,false,...,false,true]` |
| 2    | Sends an order with incorrect quantities | `"quantities":{"error":"datacenter unreachable"}` |
| 3    | Sends an order with less quantities than values | `"prices":[66.15,50.05],"quantities":[7]` |
| 4    | Sends an order with more quantities than values | `"prices":[33.39,33.81],"quantities":[5,4,8]` |
| 5    | Sends an order with an incorrect country value | `country='Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch'` |
| 6    | Sends an order with no country specified | N/A |
| 7    | Sends an order with no price specified | N/A |
| 8    | Sends an order with no quantity specified | N/A |
| 9    | Sends an order with no reduction specified | N/A |
| 10   | Sends a null order | `null` |

Sending order
