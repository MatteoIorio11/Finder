Assume there is a GitHub.com repository and it's local clone.
The branch branchA is present in both: remote and local repositories. Local branchA is not necessarily synchronized with the remote branchA.
The local branch branchB is created from branchA locally.
Develop a library that can find all files with the same path that were changed in both branchA (remotely) and branchB (locally) independently since the merge base commit (latest common commit). Please assume that there is only one merge base between the branches, the same remotely and locally. Avoid fetching branchA in the library.
You may ignore files that were changed, then rolled back.
Values owner, repo, accessToken, localRepoPath, branchA, branchB are provided as parameters.
It is assumed that for requests to the remote repository, the applicant will use GitHub REST or GraphQL API through any general-purpose HTTP client library and run git commands on the local repository via the command line execution.
Libraries specific to GitHub and Git itself should not be used.
Applicants are expected to have production level code, with tests and error handling. Results are accepted in the form of links to repositories on one or another VCS hosting service.
We value interaction with candidates, so please do not hesitate to ask for clarification in respect to the task as needed.
