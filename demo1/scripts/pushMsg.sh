project=REPLACE_WITH_PROJECT_NAME
topicName=REPLACE_WITH_TOPIC_NAME
topic=projects/$project/topics/$topicName
access_token=$(gcloud auth print-access-token)

counter="1"
DELAY=$1

while true; do
echo "Sending message $counter"
cat <<EOF |
{
  "messages": [
    {
      "data": "$(echo -n "Message ${counter}" | base64)"
    }
  ]
}
EOF
jq -Mc '.' |
    curl -s -X POST \
        https://pubsub.googleapis.com/v1/$topic:publish \
        -H "Authorization: Bearer ${access_token}" \
        -H "Content-Type: application/json" \
        -d @- >/dev/null 2>1

    ((counter++))
    if [ ! -z $DELAY ]; then
    sleep $DELAY
fi
done