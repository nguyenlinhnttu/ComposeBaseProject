#!/bin/bash
echo "Check code: ./gradlew ktlint"
echo "Report check code: ./gradlew ktlintReport"
echo "Auto format code: ./gradlew ktlintFormat"
echo "==========####=========="
echo "BSV Running check ktlint..."

./gradlew app:ktlint --daemon

status=$?

# return 1 exit code if running checks fails
[ $status -ne 0 ] && exit 1
exit 0