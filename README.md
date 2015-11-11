# spark-examples

## FIFO job schedulling example
```
$ ./multi-job-pi.sh 20 6 fifo
```

## FAIR job schedulling example
```
$ ./multi-job-pi.sh 20 6 fair
```

FAIR needs the following file /etc/spark/conf/fairscheduler.xml to configure default pool

```
<?xml version="1.0"?>
<allocations>
  <pool name="default">
    <schedulingMode>FAIR</schedulingMode>
    <weight>1</weight>
    <minShare>2</minShare>
  </pool>
</allocations>
```
