
SELECT source_id, SUM(amount) as total FROM transfers
WHERE transfer_time >= '2019-01-01'
GROUP BY source_id
HAVING SUM(amount) > 1000