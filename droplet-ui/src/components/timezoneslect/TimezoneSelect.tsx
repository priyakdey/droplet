import { SelectItem } from "@/components/ui/select.tsx";
import { memo } from "react";

interface TimezoneSelectPropsType {
  timezones: string[];
}

function TimezoneSelectComponent({ timezones }: TimezoneSelectPropsType) {
  return (
    <>
      {
        timezones.map((tz) => (
          <SelectItem key={tz} value={tz}>
            {tz}
          </SelectItem>
        ))}
    </>
  );
}

const TimezoneSelect = memo(TimezoneSelectComponent);
export default TimezoneSelect;