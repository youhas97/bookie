Command line instructions




Git global setup



git config --global user.name "Yousef Hashem"
git config --global user.email "youha847@student.liu.se"

Create a new repository

git clone https://gitlab.ida.liu.se/tddd78-2017/tddd78-projekt-dx-17.git

cd tddd78-projekt-dx-17

touch README.md

git add README.md

git commit -m "add README"

git push -u origin master




Existing folder


cd existing_folder

git init

git remote add origin https://gitlab.ida.liu.se/tddd78-2017/tddd78-projekt-dx-17.git

git add .

git commit

git push -u origin master




Existing Git repository


cd existing_repo

git remote add origin https://gitlab.ida.liu.se/tddd78-2017/tddd78-projekt-dx-17.git

git push -u origin --all

git push -u origin --tags



:D