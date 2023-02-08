#!/bin/sh

echo deploy source connector...
curl -X POST -H "Content-Type: application/json" --data @source.json http://localhost:8083/connectors

echo deploy sink connector...
curl -X POST -H "Content-Type: application/json" --data @sink.json http://localhost:8083/connectors
