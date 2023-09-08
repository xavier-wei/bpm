SELECT A.[unit_name]
      ,A.[PEIDNO]
      ,A.[PECARD]
      ,A.[PENAME]
   ,p1.posname
   ,F1.[unit_name] F1_unit_name
   ,p2.id F1_id
   ,F1.PECARD F1_account
   ,F1.PENAME F1_PENAME
   ,p2.posname F1_posname
   ,F2.[unit_name] F2_unit_name
   ,p3.id F2_id
   ,F2.PECARD F2_account
   ,F2.PENAME F2_PENAME
   ,p3.posname F2_posname

  FROM [eip].[dbo].[view_cpape05m] A
  left join position p1 on  A.PEIDNO =  p1.id
  left join position p2 on  p1.fid =  p2.posid
  left join [view_cpape05m] F1 on F1.PEIDNO = p2.id
  left join position p3 on  p2.fid =  p3.posid
  left join [view_cpape05m] F2 on F2.PEIDNO = p3.id

  where 1=1
  and A.[PEORG] = '360000000G'
  and A.PECARD = '1510'
