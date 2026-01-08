: ${HOST=localhost}
: ${PORT=8080}

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker compose down --remove-orphans"
    docker compose down --remove-orphans
    echo "$ docker compose up -d"
    docker compose up -d
fi

function testUrl() {
    local url="$1"
    curl "$url" -ks -f -o /dev/null
}

function waitForService() {
    local url="$1"
    echo -n "Wait for: $url... "
    local n=0

    until testUrl "$url"
    do
        n=$((n+1))
        if [[ $n -eq 100 ]]
        then
            echo "Give up"
            exit 1
        else
            sleep 3
            echo -n ", retry #$n"
        fi
    done
    echo " Done, continues..."
}

waitForService "http://$HOST:${PORT}/product-composite/1"

if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping test environment..."
    echo "$ docker compose down"
    docker compose down
fi