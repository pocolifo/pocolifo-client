for file in $(find ./build/tmp/generateVersionPublishFiles -type f); do
  upload_path=${file/"./build/tmp/generateVersionPublishFiles/"/}
  echo "Upload $upload_path"
  curl https://maven.services.pocolifo.com/pocolifoclient/v2/$upload_path -u ${REPO_USERNAME}:${REPO_PASSWORD} --upload-file $file
  echo ""
done
