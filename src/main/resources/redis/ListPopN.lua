if redis.call('setnx', KEYS[1], ARGV[1]) then
    return redis.call('expire', KEYS[1], ARGV[2])
else
    return -1000
end